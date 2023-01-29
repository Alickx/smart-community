package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.CommonConstant;
import cn.goroute.smart.common.constant.ErrorCodeEnum;
import cn.goroute.smart.post.converter.PostConverter;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.manage.PostManageService;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.model.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.model.dto.PostBaseDTO;
import cn.goroute.smart.post.model.dto.PostInfoDTO;
import cn.goroute.smart.post.model.qo.PostQO;
import cn.goroute.smart.post.model.vo.PostVO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Alickx
* @description 针对表【post(文章表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
@RequiredArgsConstructor
public class PostServiceImpl extends ExtendServiceImpl<PostMapper, Post>
    implements PostService{

	private final PostManageService postManageService;
	private final Ip2regionSearcher ip2regionSearcher;
	private final CommentService commentService;


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
		List<? extends PostBaseDTO> postDTOList = postManageService.fillInfo(records);

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

		PostInfoDTO postInfoDTO = PostConverter.INSTANCE.poToDto(post);
		IpInfo ipInfo = ip2regionSearcher.search(post.getIp());

		if (ipInfo != null) {
			postInfoDTO.setRegion(ipInfo.getProvince());
		}

		List<? extends PostBaseDTO> postBaseDTOS =
				postManageService.fillInfo(Lists.asList(postInfoDTO, new PostInfoDTO[0]));

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
		post.setIp(ipAddr);

		//TODO 待完善 积分增加，文章数增加，风控检查处理等
		postManageService.savePost2Db(post);
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

		postManageService.fillInfo(postAbbreviationDTOS);

		PageResult<PostAbbreviationDTO> pageResult =
				new PageResult<>(postAbbreviationDTOS, result.getTotal());

		return R.ok(pageResult);
	}
}




