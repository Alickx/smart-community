package cn.goroute.smart.post.modules.comment.manager;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.StatusConstant;
import cn.goroute.smart.common.constant.enums.BooleanEnum;
import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.common.modules.result.SystemResultCode;
import cn.goroute.smart.common.util.PageUtil;
import cn.goroute.smart.post.constant.enums.UserInteractTypeEnum;
import cn.goroute.smart.post.domain.dto.ContentExpansionDTO;
import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.post.domain.entity.UserInteractEntity;
import cn.goroute.smart.post.domain.qo.CommentQO;
import cn.goroute.smart.post.domain.vo.CommentVO;
import cn.goroute.smart.post.feign.FeignUserProfileService;
import cn.goroute.smart.post.modules.article.mapper.PostMapper;
import cn.goroute.smart.post.modules.article.service.UserInteractService;
import cn.goroute.smart.post.modules.comment.converter.converter.CommentConverter;
import cn.goroute.smart.post.modules.comment.mapper.CommentMapper;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Alickx
 * @Date: 2022/12/04/15:19
 * @Description:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentManagerService {

    private final FeignUserProfileService feignUserProfileService;
    private final CommentMapper commentMapper;
    private final UserInteractService userInteractService;
    private final PostMapper postMapper;


    public void fillInfo(List<CommentVO> records) {

        // 获取用户信息
        this.getUserProfile(records);

        // 获取二级回复列表
        this.getPageReplyList(records);

        // 获取拓展信息
        this.getCommentExpansion(records, UserInteractTypeEnum.COMMENT.getCode());

    }

    private void getPageReplyList(List<CommentVO> records) {

        PageParam pageParam = new PageParam();
        pageParam.setPage(1);
        pageParam.setSize(3);
        PageParam.Sort sort = new PageParam.Sort();
        sort.setField("create_time");
        sort.setAsc(false);
        pageParam.setSorts(Lists.newArrayList(sort));

        records.forEach(record -> {

            // 如果本次查询的评论为二级评论，则不需要查询
            if (record.getFirstCommentId() != null) {
                return;
            }

            // 获取二级回复列表

            IPage<CommentEntity> prodPage = PageUtil.prodPage(pageParam);

            CommentQO commentQO = new CommentQO();
            commentQO.setPostId(record.getPostId());
            commentQO.setFirstCommentId(record.getId());
            IPage<CommentEntity> commentEntityPage = commentMapper
                    .queryPage(prodPage, commentQO, StatusConstant.NORMAL_STATUS, StatusConstant.NORMAL_STATUS);

            IPage<CommentVO> commentVOIPage = commentEntityPage.convert(CommentConverter.INSTANCE::poToVO);

            List<CommentVO> replyRecords = commentVOIPage.getRecords();

            // 二级回复查询用户信息
            this.getUserProfile(replyRecords);

            // 二级回复查询拓展信息
            this.getCommentExpansion(replyRecords, UserInteractTypeEnum.REPLY.getCode());

            // 填充
            PageResult<CommentVO> commentDTOPageResult = new PageResult<>(replyRecords, commentVOIPage.getTotal());
            record.setReplyList(commentDTOPageResult);
        });

    }

    /**
     * 检查是否拥有更多回复
     *
     * @param commentVO 评论dto
     * @return 是否拥有更多回复 true:有 false:没有
     */
    private Boolean checkIsMoreReply(CommentVO commentVO) {
        if (null != commentVO.getReplyList()) {
            return commentVO.getReplyList().getTotal() > commentVO.getReplyList().getRecords().size();
        }
        return false;
    }


    private void getCommentExpansion(List<CommentVO> records, Integer type) {

        if (CollUtil.isEmpty(records)) {
            return;
        }

        boolean login = StpUtil.isLogin();

        Long userId = login ? StpUtil.getLoginIdAsLong() : null;

        if (!login) {

            for (CommentVO record : records) {
                ContentExpansionDTO contentExpansionDTO = ContentExpansionDTO.create();
                record.setExpansion(contentExpansionDTO);
            }

            return;
        }

        List<Long> commentIds = records.stream().map(CommentVO::getId).toList();
        List<UserInteractEntity> userInteractEntityList = userInteractService.batchGetUserPostInteract(commentIds, type, userId);
        Map<Long, UserInteractEntity> userInteractMap = new HashMap<>(userInteractEntityList.size());

        for (UserInteractEntity userInteractEntity : userInteractEntityList) {
            userInteractMap.put(userInteractEntity.getTargetId(), userInteractEntity);
        }

        // 获取文章id集合
        for (CommentVO record : records) {

            ContentExpansionDTO contentExpansionDTO = ContentExpansionDTO.create();

            UserInteractEntity userInteractEntity = userInteractMap.get(record.getId());
            if (userInteractEntity != null) {
                // 查询点赞信息
                contentExpansionDTO.setIsThumb(userInteractEntity.getIsThumb().equals(BooleanEnum.TRUE.intValue()));
                // 判断是否拥有更多回复
                contentExpansionDTO.setIsMoreReply(this.checkIsMoreReply(record));
                // 判断是否是作者
                contentExpansionDTO.setIsAuthor(record.getUserId().equals(userId));
                record.setExpansion(contentExpansionDTO);
                continue;
            }
            record.setExpansion(contentExpansionDTO);
        }
    }


    private void getUserProfile(List<CommentVO> records) {

        if (CollUtil.isNotEmpty(records)) {
            List<Long> userIds = records.stream().map(CommentVO::getUserId).toList();
            R<List<UserProfileVO>> resp = feignUserProfileService.batchGetUserProfile(userIds);
            if (resp.getCode() == SystemResultCode.SUCCESS.getCode() && resp.getData() != null) {

                Map<Long, UserProfileVO> userProfileMap = new HashMap<>();

                for (UserProfileVO userProfileVO : resp.getData()) {
                    userProfileMap.put(userProfileVO.getUserId(), userProfileVO);
                }
                for (CommentVO commentVO : records) {
                    commentVO.setUserProfile(userProfileMap.get(commentVO.getUserId()));
                }
            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void saveCommentHandle(CommentEntity commentEntity) {

        // 保存评论
        commentMapper.insert(commentEntity);

        // 更新文章评论数
        postMapper.incrCommentCount(commentEntity.getPostId());

        // 更新用户文章关系
        userInteractService.updateUserCommentRelation(commentEntity, true);
    }
}
