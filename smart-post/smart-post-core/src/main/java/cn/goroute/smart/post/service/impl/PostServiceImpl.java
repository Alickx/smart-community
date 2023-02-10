package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.CommonConstant;
import cn.goroute.smart.common.constant.ErrorCodeEnum;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.post.constant.PostStateEnum;
import cn.goroute.smart.post.converter.PostConverter;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.manager.PostManagerService;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.model.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.model.dto.PostBaseDTO;
import cn.goroute.smart.post.model.dto.PostInfoDTO;
import cn.goroute.smart.post.model.dto.PostViewRankDTO;
import cn.goroute.smart.post.model.qo.PostQO;
import cn.goroute.smart.post.model.vo.PostVO;
import cn.goroute.smart.post.mq.PostRiskCheckEventMessageTemplate;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.post.service.PostService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.util.IpUtils;
import com.hccake.ballcat.starter.ip2region.core.IpInfo;
import com.hccake.ballcat.starter.ip2region.searcher.Ip2regionSearcher;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
* @author Alickx
* @description 针对表【post(文章表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl extends ExtendServiceImpl<PostMapper, Post>
    implements PostService{

	private final PostManagerService postManagerService;
	private final Ip2regionSearcher ip2regionSearcher;
	private final CommentService commentService;
	private final PostRiskCheckEventMessageTemplate postRiskCheckEventMessageTemplate;
	private final RedisUtil redisUtil;


	/**
	 * 文章详情 - 分页查询
	 * @param pageParam 分页参数
	 * @param postQO 查询参数对象
	 * @return 查询结果
	 */
	@Override
	public R<PageResult<PostAbbreviationDTO>> infoPage(PageParam pageParam, PostQO postQO) {
		PageResult<PostAbbreviationDTO> postPage = baseMapper.queryPage(pageParam, postQO);
		List<PostAbbreviationDTO> records = postPage.getRecords();

		if (CollUtil.isEmpty(records)) {
			return R.ok(postPage);
		}

		// 补充文章作者，板块等信息
		List<? extends PostBaseDTO> postDTOList = postManagerService.fillInfo(records);

		postPage.setRecords((List<PostAbbreviationDTO>) postDTOList);

		return R.ok(postPage);

	}

	/**
	 * 根据文章Id查询文章详情
	 *
	 * @param postId 文章Id
	 * @return 文章详情
	 */
	@Override
	public R<PostInfoDTO> info(Long postId) {


		LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Post::getId, postId);
		wrapper.notIn(Post::getDeleted, CommonConstant.DELETE_STATE);
		Post post = baseMapper.selectOne(wrapper);

		if (post == null) {
			return R.failed(ErrorCodeEnum.POST_NOT_EXIST);
		}

		long loginIdAsLong = StpUtil.getLoginIdAsLong();
		Long authorId = post.getAuthorId();

		if (!authorId.equals(loginIdAsLong)) {
			// 如果非作者访问，需要判断文章的属性
			if (!post.getIsPublish()) {
				return R.failed(ErrorCodeEnum.POST_NOT_PUBLISH);
			}

			if (!post.getState().equals(PostStateEnum.NORMAL.getCode())) {
				return R.failed(ErrorCodeEnum.POST_HAS_RISK);
			}
		}

		PostInfoDTO postInfoDTO = PostConverter.INSTANCE.poToDto(post);
		IpInfo ipInfo = ip2regionSearcher.search(post.getIp());

		if (ipInfo != null) {
			postInfoDTO.setRegion(ipInfo.getProvince());
		}

		List<? extends PostBaseDTO> postBaseDTOS =
				postManagerService.fillInfo(Lists.asList(postInfoDTO, new PostInfoDTO[0]));

		// 缓存文章阅读数和排行榜
		if (StpUtil.isLogin() && !authorId.equals(loginIdAsLong)) {
			// 判断是否已经阅读过
			if (!redisUtil.sIsMember(PostConstant.Post.POST_VIEW_COUNT_KEY + postId, StpUtil.getLoginIdAsString())) {
				// 增加文章阅读数
				redisUtil.sAdd(PostConstant.Post.POST_VIEW_COUNT_KEY + postId, StpUtil.getLoginIdAsString());

				// 添加进今日的排行榜中
				redisUtil.zIncrementScore(PostConstant.Post.getTodayPostViewCountKey(), String.valueOf(postId), 1);
			}
		}

		return R.ok((PostInfoDTO) postBaseDTOS.get(0));
	}

	/**
	 * 保存文章
	 *
	 * @param postVO 文章视图对象
	 * @return 文章Id
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public R<Long> savePost(PostVO postVO, HttpServletRequest request) {

		Post post = PostConverter.INSTANCE.voToPo(postVO);
		post.setAuthorId(StpUtil.getLoginIdAsLong());
		String ipAddr = IpUtils.getIpAddr(request);
		post.setIp(ipAddr.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ipAddr);

		postManagerService.savePost2Db(post);

		// 风控检查
		postRiskCheckEventMessageTemplate.sendPostMessage(post);

		return R.ok(post.getId());
	}

	/**
	 * 查询评论过的文章
	 *
	 * @param pageParam 分页参数
	 * @param postQO    查询参数对象
	 * @return 查询结果
	 */
	@Override
	public R<PageResult<PostAbbreviationDTO>> queryByComment(PageParam pageParam, PostQO postQO) {

		PageResult<Long> result = commentService.queryPostIdsByComment(pageParam, postQO);

		if (CollUtil.isEmpty(result.getRecords())) {
			return R.ok(new PageResult<>());
		}
		List<Post> posts = baseMapper.selectBatchIds(result.getRecords());

		if (CollUtil.isEmpty(posts)) {
			return R.ok(new PageResult<>());
		}

		List<PostAbbreviationDTO> postAbbreviationDTOS = posts.stream()
				.map(PostConverter.INSTANCE::poToAbbreviationDto)
				.toList();

		postManagerService.fillInfo(postAbbreviationDTOS);

		PageResult<PostAbbreviationDTO> pageResult =
				new PageResult<>(postAbbreviationDTOS, result.getTotal());

		return R.ok(pageResult);
	}


	@Override
	public void PostRiskHandler(Post post) {

		log.info("文章风控处理，文章id：{}", post.getId());
		Post postEntity = this.getById(post.getId());

		postEntity.setState(post.getState());

		this.updateById(postEntity);

		// 同步到es
		postManagerService.sync2Es(postEntity);

	}

	@Override
	public R<List<PostViewRankDTO>> queryTodayViewRank() {

		Set<String> postIdSet = redisUtil
				.zReverseRangeByScore(PostConstant.Post.getTodayPostViewCountKey(), 0, 5);

		if (CollUtil.isEmpty(postIdSet)) {
			return R.ok(new ArrayList<>());
		}

		List<Post> posts = this.listByIds(postIdSet);

		List<PostViewRankDTO> result = new ArrayList<>();

		for (String postId : postIdSet) {

			posts.stream()
					.filter(post -> post.getId().equals(Long.parseLong(postId)))
					.findFirst().ifPresent(p -> result.add(PostConverter.INSTANCE.poToViewRankDto(p)));

		}

		return R.ok(result);

	}
}




