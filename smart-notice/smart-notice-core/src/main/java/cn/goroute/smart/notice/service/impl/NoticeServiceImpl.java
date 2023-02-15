package cn.goroute.smart.notice.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.StatusConstant;
import cn.goroute.smart.notice.constant.enums.MsgTypeEnum;
import cn.goroute.smart.notice.constant.enums.SourceTypeEnum;
import cn.goroute.smart.notice.domain.dto.NoticeCountVO;
import cn.goroute.smart.notice.domain.entity.NoticeEntity;
import cn.goroute.smart.notice.domain.entity.NoticeMessageInfoEntity;
import cn.goroute.smart.notice.domain.vo.NoticeMessageVO;
import cn.goroute.smart.notice.manager.NoticeManagerService;
import cn.goroute.smart.notice.mapper.NoticeMapper;
import cn.goroute.smart.notice.service.NoticeService;
import cn.goroute.smart.post.domain.dto.CommentDTO;
import cn.goroute.smart.post.domain.dto.ThumbDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author alickx
 * @description 针对表【t_notice(站内通知)】的数据库操作Service实现
 * @createDate 2023-02-14 14:14:44
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeEntity>
        implements NoticeService {

    private final NoticeMapper noticeMapper;
    private final NoticeManagerService noticeManagerService;

    @Override
    public void saveThumbNotice(ThumbDTO thumbDTO) {

        // 查询该点赞通知是否已经存在
        NoticeEntity noticeEntity = noticeMapper.
                queryNoticeIsExist(thumbDTO.getUserId(), thumbDTO.getToUserId(),
                        MsgTypeEnum.THUMB.getCode(), thumbDTO.getId());

        if (null == noticeEntity) {

            // 判断是否是自己给自己点赞
            if (thumbDTO.getUserId().equals(thumbDTO.getToUserId())) {
                return;
            }

            // 创建通知
            NoticeMessageInfoEntity noticeMessageInfoEntity = new NoticeMessageInfoEntity();
            noticeMessageInfoEntity.setTargetId(thumbDTO.getId());
            noticeMessageInfoEntity.setPostId(thumbDTO.getPostId());
            noticeMessageInfoEntity.setTitle("点赞通知");
            noticeMessageInfoEntity.setSourceType(thumbDTO.getType());
            noticeMessageInfoEntity.setContent(thumbDTO.getContent());
            noticeMessageInfoEntity.setMsgType(MsgTypeEnum.THUMB.getCode());

            noticeManagerService.saveNoticeMessage(noticeMessageInfoEntity, thumbDTO.getUserId(), thumbDTO.getToUserId());
        }


    }

    @Override
    public void saveCommentNotice(CommentDTO commentDTO) {

        // 查询该评论通知是否已经存在
        NoticeEntity noticeEntity = noticeMapper.
                queryNoticeIsExist(commentDTO.getUserId(), commentDTO.getToUserId(),
                        MsgTypeEnum.COMMENT.getCode(), commentDTO.getId());

        if (null == noticeEntity) {

            // 判断是否是自己给自己评论
            if (commentDTO.getUserId().equals(commentDTO.getToUserId())) {
                return;
            }

            // 创建通知
            NoticeMessageInfoEntity noticeMessageInfoEntity = new NoticeMessageInfoEntity();
            ;
            noticeMessageInfoEntity.setTargetId(commentDTO.getId());
            noticeMessageInfoEntity.setPostId(commentDTO.getPostId());
            noticeMessageInfoEntity.setTitle("评论通知");
            noticeMessageInfoEntity.setSourceType(SourceTypeEnum.COMMENT.getCode());
            noticeMessageInfoEntity.setContent(commentDTO.getContent());
            noticeMessageInfoEntity.setMsgType(MsgTypeEnum.COMMENT.getCode());

            noticeManagerService.saveNoticeMessage(noticeMessageInfoEntity, commentDTO.getUserId(), commentDTO.getToUserId());
        }

    }

    @Override
    public R<List<NoticeCountVO>> queryNoticeCount() {

        long userId = StpUtil.getLoginIdAsLong();

        List<NoticeCountVO> noticeCountVOS = noticeMapper.queryNoticeCount(userId, StatusConstant.NORMAL_STATUS);

        return R.ok(noticeCountVOS);
    }


    @Override
    public R<PageResult<NoticeMessageVO>> pageNotice(Integer type, PageParam pageParam) {

        long userId = StpUtil.getLoginIdAsLong();;

        Page<NoticeMessageVO> page = new Page<>(pageParam.getPage(), pageParam.getSize());

        IPage<NoticeMessageVO> pageRecord = noticeMapper.pageNotice(userId, type, page);

        return R.ok(new PageResult<>(pageRecord.getRecords(),pageRecord.getTotal()));

    }
}




