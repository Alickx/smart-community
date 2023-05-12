package cn.goroute.smart.post.modules.article.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.StatusConstant;
import cn.goroute.smart.common.constant.enums.BooleanEnum;
import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.exception.BusinessException;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.common.util.PageUtil;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.common.util.WebUtil;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.constant.enums.PostItemTypeEnum;
import cn.goroute.smart.post.constant.enums.PostStatusEnum;
import cn.goroute.smart.post.domain.ExpandInfoEntity;
import cn.goroute.smart.post.domain.entity.CategoryEntity;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.entity.TagEntity;
import cn.goroute.smart.post.domain.form.PostQueryForm;
import cn.goroute.smart.post.domain.qo.PostQO;
import cn.goroute.smart.post.domain.vo.PostAbbreviationVO;
import cn.goroute.smart.post.domain.vo.PostBaseVO;
import cn.goroute.smart.post.domain.vo.PostInfoVO;
import cn.goroute.smart.post.domain.vo.PostVO;
import cn.goroute.smart.post.modules.article.async.PostAsyncService;
import cn.goroute.smart.post.modules.article.converter.PostConverter;
import cn.goroute.smart.post.modules.article.manager.PostManagerService;
import cn.goroute.smart.post.modules.article.mapper.PostMapper;
import cn.goroute.smart.post.modules.article.mq.event.PostPublicEventMessage;
import cn.goroute.smart.post.modules.article.mq.event.PostSyncEventMessageTemplate;
import cn.goroute.smart.post.modules.article.service.*;
import cn.goroute.smart.post.modules.comment.service.CommentService;
import cn.goroute.smart.post.util.MarkdownUtil;
import cn.goroute.smart.user.domain.dto.UserCollectEventDTO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hccake.ballcat.starter.ip2region.core.IpInfo;
import com.hccake.ballcat.starter.ip2region.searcher.Ip2regionSearcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alickx
 * @description 针对表【post(文章表)】的数据库操作Service实现
 * @createDate 2022-09-25 16:53:24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper, PostEntity>
	implements PostService {

	private final PostManagerService postManagerService;
	private final Ip2regionSearcher ip2regionSearcher;
	private final CommentService commentService;
	private final RedisUtil redisUtil;
	private final TagService tagService;
	private final CategoryService categoryService;
	private final PostAsyncService postAsyncService;
	private final PostMapper postMapper;
	private final PostSyncEventMessageTemplate postSyncEventMessageTemplate;
	private final PostPublicEventMessage postPublicEventMessage;
	private final UserInteractService userInteractService;


	/**
	 * 文章详情 - 分页查询
	 *
	 * @param pageParam 分页参数
	 * @param postQO    查询参数对象
	 * @return 查询结果
	 */
	@Override
	public PageResult<PostAbbreviationVO> infoPage(PageParam pageParam, PostQO postQO) {

		IPage<PostEntity> prodPage = PageUtil.prodPage(pageParam);

		IPage<PostEntity> postEntityIPage = baseMapper.queryPage(prodPage, postQO, StatusConstant.NORMAL_STATUS, StatusConstant.NORMAL_STATUS);

		IPage<PostAbbreviationVO> postPage = postEntityIPage.convert(PostConverter.INSTANCE::poToAbbreviationDto);

		List<PostAbbreviationVO> records = postPage.getRecords();

		if (CollUtil.isEmpty(records)) {
			return new PageResult<>();
		}

		// 补充文章作者，板块等信息
		List<? extends PostBaseVO> postDTOList = postManagerService.fillInfo(records);


		return (PageResult<PostAbbreviationVO>) new PageResult<>(postDTOList, postPage.getTotal());

	}

	/**
	 * 根据文章Id查询文章详情
	 *
	 * @param postId 文章Id
	 * @return 文章详情
	 */
	@Override
	public R<PostInfoVO> info(Long postId) {

		boolean isLogin = StpUtil.isLogin();

		PostEntity postEntity = baseMapper.selectById(postId);

		if (postEntity == null) {
			return R.failed(ErrorCodeEnum.POST_NOT_EXIST);
		}

		Long authorId = postEntity.getAuthorId();

		if (isLogin) {

			long loginIdAsLong = StpUtil.getLoginIdAsLong();

			if (!authorId.equals(loginIdAsLong)) {
				// 如果非作者访问，需要判断文章的属性
				if (!postEntity.getIsPublish()) {
					return R.failed(ErrorCodeEnum.POST_NOT_PUBLISH);
				}

				if (!postEntity.getState().equals(PostStatusEnum.NORMAL.getCode())) {
					return R.failed(ErrorCodeEnum.POST_HAS_RISK);
				}
			}

			// 添加进文章访问缓存中，使用set存储
			String redisKey = PostRedisConstant.PostKey.POST_READ_KEY + postId;
			redisUtil.sAdd(redisKey, String.valueOf(loginIdAsLong));

		}

		PostInfoVO postInfoDTO = PostConverter.INSTANCE.poToDto(postEntity);
		IpInfo ipInfo = ip2regionSearcher.search(postEntity.getIp());

		if (ipInfo != null) {
			postInfoDTO.setRegion(ipInfo.getProvince());
		}

		List<? extends PostBaseVO> postBaseDTOS =
			postManagerService.fillInfo(Lists.asList(postInfoDTO, new PostInfoVO[0]));


		return R.ok((PostInfoVO) postBaseDTOS.get(0));
	}

	/**
	 * 保存文章
	 *
	 * @param postVO 文章视图对象
	 * @return 文章Id
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public R<Long> savePost(PostVO postVO) {

		PostEntity postEntity = PostConverter.INSTANCE.voToPo(postVO);
		postEntity.setAuthorId(StpUtil.getLoginIdAsLong());
		String ipAddr = ServletUtil.getClientIP(WebUtil.getRequest());
		postEntity.setIp("0:0:0:0:0:0:0:1".equals(ipAddr) ? "127.0.0.1" : ipAddr);

		if (checkParams(postVO)) {
			return R.failed(ErrorCodeEnum.PARAM_ERROR);
		}

		postEntity.setState(PostStatusEnum.NORMAL.getCode());

		// 保存文章信息
		postEntity.setSummary(getPostSummary(postEntity.getContent()));
		this.saveOrUpdate(postEntity);

		// 发送用户发布文章事件
		postPublicEventMessage.sendEvent(postEntity.getId(), postEntity.getAuthorId());

		// 同步到搜索服务
		postAsyncService.getAndSendPostIndexEvent(postEntity.getId());

		return R.ok(postEntity.getId());
	}

	private String getPostSummary(String content) {
		// markdown转文本
		String text = MarkdownUtil.markdownToText(content);

		// 截取前200个字符
		if (text.length() > 200) {
			text = text.substring(0, 200);
		}
		return text;
	}

	private boolean checkParams(PostVO postVO) {

		TagEntity tagEntity = tagService.queryByName(postVO.getTagName());
		if (tagEntity == null) {
			return true;
		}

		CategoryEntity categoryEntity = categoryService.queryByName(postVO.getCategoryName());

		return categoryEntity == null;
	}

	/**
	 * 查询评论过的文章
	 *
	 * @param pageParam 分页参数
	 * @param postQO    查询参数对象
	 * @return 查询结果
	 */
	@Override
	public R<PageResult<PostAbbreviationVO>> queryByComment(PageParam pageParam, PostQO postQO) {

		if (postQO.getUserId() == null) {
			return R.ok(new PageResult<>());
		}

		PageResult<Long> result = commentService.queryPostIdsByComment(pageParam, postQO.getUserId());

		if (CollUtil.isEmpty(result.getRecords())) {
			return R.ok(new PageResult<>());
		}
		List<PostEntity> postEntities = baseMapper.selectBatchIds(result.getRecords());

		if (CollUtil.isEmpty(postEntities)) {
			return R.ok(new PageResult<>());
		}

		List<PostAbbreviationVO> postAbbreviationVOS = postEntities.stream()
			.map(PostConverter.INSTANCE::poToAbbreviationDto)
			.toList();

		postManagerService.fillInfo(postAbbreviationVOS);

		PageResult<PostAbbreviationVO> pageResult =
			new PageResult<>(postAbbreviationVOS, result.getTotal());

		return R.ok(pageResult);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> deletePost(Long postId) {

		long userId = StpUtil.getLoginIdAsLong();

		PostEntity postEntity = postMapper.selectById(postId);

		if (postEntity.getAuthorId() != userId) {
			return R.failed(ErrorCodeEnum.NO_PERMISSION);
		}

		// 逻辑删除文章
		postMapper.deleteById(postId);

		postEntity.setDeleted(StatusConstant.DELETE_STATUS);

		postSyncEventMessageTemplate.sendPostMessage(postEntity);

		return R.ok(true);
	}

	/**
	 * 批量查询文章简略信息
	 *
	 * @param postIds 文章Id集合
	 * @return 文章简略信息集合
	 */
	@Override
	public List<PostAbbreviationVO> batchInfo(List<Long> postIds) {

		List<PostEntity> postEntities = this.listByIds(postIds);

		if (CollUtil.isEmpty(postEntities)) {
			return new ArrayList<>();
		}

		List<PostAbbreviationVO> postAbbreviationVOS = postEntities.stream()
			.map(PostConverter.INSTANCE::poToAbbreviationDto)
			.toList();

		postManagerService.fillInfo(postAbbreviationVOS);

		return postAbbreviationVOS;
	}

	/**
	 * 判断文章是否存在
	 *
	 * @param postId 文章Id
	 * @return 是否存在 true:存在 false:不存在
	 */
	@Override
	public Boolean queryIsExist(Long postId) {

		PostEntity postEntity = postMapper.selectById(postId);

		return postEntity != null;

	}

	/**
	 * 文章收藏处理
	 *
	 * @param userCollectEventDTO 用户收藏事件DTO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void collectPostHandle(UserCollectEventDTO userCollectEventDTO) {

		Long userId = userCollectEventDTO.getUserId();
		Long postId = userCollectEventDTO.getPostId();
		Integer collectStatus = userCollectEventDTO.getCollectStatus();

		String redis = PostRedisConstant.PostKey.EXPAND_INFO_KEY + PostItemTypeEnum.POST.getName() + ":" + postId;
		// 增加文章收藏数
		switch (collectStatus) {
			case 0 -> {
				// 减少文章收藏数
				redisUtil.hIncrBy(redis, ExpandInfoEntity.Fields.collectCount, -1);
				// 保存用户关系
				userInteractService.updateUserCollectPost(userId, postId, BooleanEnum.FALSE.booleanValue());
			}
			case 1 -> {
				// 增加文章收藏数
				redisUtil.hIncrBy(redis, ExpandInfoEntity.Fields.collectCount, 1);
				// 保存用户关系
				userInteractService.updateUserCollectPost(userId, postId, BooleanEnum.TRUE.booleanValue());
			}
			default -> throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
		}

	}


	@Override
	public PageResult<PostEntity> pageQuery(PageParam pageParam, PostQueryForm form) {

		IPage<PostEntity> prodPage = PageUtil.prodPage(pageParam);

		LambdaQueryWrapper<PostEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(StrUtil.isNotBlank(form.getTitle()), PostEntity::getTitle, form.getTitle());
		wrapper.eq(form.getPostId() != null, PostEntity::getId, form.getPostId());
		wrapper.eq(form.getUserId() != null, PostEntity::getAuthorId, form.getUserId());

		IPage<PostEntity> selectPage = this.baseMapper.selectPage(prodPage, wrapper);

		return PageUtil.prodPageResult(selectPage);
	}
}




