package cn.goroute.smart.post.modules.article.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.StatusConstant;
import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.common.util.PageUtil;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.common.util.WebUtil;
import cn.goroute.smart.post.modules.article.async.PostAsyncService;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.constant.enums.PostStateEnum;
import cn.goroute.smart.post.modules.article.converter.PostConverter;
import cn.goroute.smart.post.domain.dto.PostViewRankDTO;
import cn.goroute.smart.post.domain.entity.CategoryEntity;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.entity.TagEntity;
import cn.goroute.smart.post.domain.qo.PostQO;
import cn.goroute.smart.post.domain.vo.PostAbbreviationVO;
import cn.goroute.smart.post.domain.vo.PostBaseVO;
import cn.goroute.smart.post.domain.vo.PostInfoVO;
import cn.goroute.smart.post.domain.vo.PostVO;
import cn.goroute.smart.post.modules.article.manager.PostManagerService;
import cn.goroute.smart.post.modules.article.mapper.PostMapper;
import cn.goroute.smart.post.modules.article.mq.PostPublicEventMessage;
import cn.goroute.smart.post.modules.article.mq.PostSyncEventMessageTemplate;
import cn.goroute.smart.post.modules.article.service.CategoryService;
import cn.goroute.smart.post.modules.comment.service.CommentService;
import cn.goroute.smart.post.modules.article.service.PostService;
import cn.goroute.smart.post.modules.article.service.TagService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hccake.ballcat.starter.ip2region.core.IpInfo;
import com.hccake.ballcat.starter.ip2region.searcher.Ip2regionSearcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Autowired
	private PostPublicEventMessage postPublicEventMessage;


	/**
     * 文章详情 - 分页查询
     *
     * @param pageParam 分页参数
     * @param postQO    查询参数对象
     * @return 查询结果
     */
    @Override
    public R<PageResult<PostAbbreviationVO>> infoPage(PageParam pageParam, PostQO postQO) {

        IPage<PostEntity> prodPage = PageUtil.prodPage(pageParam);

        IPage<PostEntity> postEntityIPage = baseMapper.queryPage(prodPage, postQO, StatusConstant.NORMAL_STATUS, StatusConstant.NORMAL_STATUS);

        IPage<PostAbbreviationVO> postPage = postEntityIPage.convert(PostConverter.INSTANCE::poToAbbreviationDto);

        List<PostAbbreviationVO> records = postPage.getRecords();

        if (CollUtil.isEmpty(records)) {
            return R.ok(new PageResult<>());
        }

        // 补充文章作者，板块等信息
        List<? extends PostBaseVO> postDTOList = postManagerService.fillInfo(records);


        PageResult<PostAbbreviationVO> pageResult =
                (PageResult<PostAbbreviationVO>) new PageResult<>(postDTOList, postPage.getTotal());

        return R.ok(pageResult);

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

                if (!postEntity.getState().equals(PostStateEnum.NORMAL.getCode())) {
                    return R.failed(ErrorCodeEnum.POST_HAS_RISK);
                }
            }
        }

        PostInfoVO postInfoDTO = PostConverter.INSTANCE.poToDto(postEntity);
        IpInfo ipInfo = ip2regionSearcher.search(postEntity.getIp());

        if (ipInfo != null) {
            postInfoDTO.setRegion(ipInfo.getProvince());
        }

        List<? extends PostBaseVO> postBaseDTOS =
                postManagerService.fillInfo(Lists.asList(postInfoDTO, new PostInfoVO[0]));

        // todo 进行优化
        // 缓存文章阅读数和排行榜
//        if (isLogin && !authorId.equals(StpUtil.getLoginIdAsLong())) {
//            handleViewCountAndRank(postId);
//        }

        return R.ok((PostInfoVO) postBaseDTOS.get(0));
    }

//    private void handleViewCountAndRank(Long postId) {
//        // 判断是否已经阅读过
//        if (!redisUtil.sIsMember(PostRedisConstant.PostKey.POST_VIEW_COUNT_KEY + postId, StpUtil.getLoginIdAsString())) {
//            // 增加文章阅读数
//            redisUtil.sAdd(PostRedisConstant.PostKey.POST_VIEW_COUNT_KEY + postId, StpUtil.getLoginIdAsString());
//
//            // 添加进今日的排行榜中
//            redisUtil.zIncrementScore(PostRedisConstant.PostKey.getTodayPostViewCountKey(), String.valueOf(postId), 1);
//        }
//    }

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
        postEntity.setIp(ipAddr.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ipAddr);

        if (checkParams(postVO)) return R.failed(ErrorCodeEnum.PARAM_ERROR);

        postEntity.setState(PostStateEnum.NORMAL.getCode());

        postManagerService.savePost2Db(postEntity);

		// 发送用户发布文章事件
		postPublicEventMessage.sendEvent(postEntity.getId(), postEntity.getAuthorId());

        postAsyncService.getAndSendPostIndexEvent(postEntity.getId());

        return R.ok(postEntity.getId());
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
    public R<List<PostViewRankDTO>> queryTodayViewRank() {

        Set<String> postIdSet = redisUtil
                .zReverseRangeByScore(PostRedisConstant.PostKey.getTodayPostViewCountKey(), 0, 5);

        if (CollUtil.isEmpty(postIdSet)) {
            return R.ok(new ArrayList<>());
        }

        List<PostEntity> postEntities = this.listByIds(postIdSet);

        List<PostViewRankDTO> result = new ArrayList<>();

        for (String postId : postIdSet) {

            postEntities.stream()
                    .filter(post -> post.getId().equals(Long.parseLong(postId)))
                    .findFirst().ifPresent(p -> result.add(PostConverter.INSTANCE.poToViewRankDto(p)));

        }

        return R.ok(result);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> deletePost(Long postId) {

        long userId = StpUtil.getLoginIdAsLong();

        PostEntity postEntity = postMapper.selectById(postId);

        if (postEntity.getAuthorId() != userId) {
            return R.failed(ErrorCodeEnum.NO_PERMISSION);
        }

        postMapper.deleteById(postId);
        postEntity.setDeleted(StatusConstant.DELETE_STATUS);

        postSyncEventMessageTemplate.sendPostMessage(postEntity);

        return R.ok(true);
    }
}




