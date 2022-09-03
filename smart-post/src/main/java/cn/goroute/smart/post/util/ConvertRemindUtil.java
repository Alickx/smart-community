package cn.goroute.smart.post.util;

import cn.goroute.smart.common.constant.NotificationConstant;
import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.common.entity.bo.EventRemindBo;
import cn.goroute.smart.post.entity.pojo.Comment;
import cn.goroute.smart.post.entity.pojo.Thumb;

/**
 * @Author: Alickx
 * @Date: 2022/03/25/15:21
 * @Description: 将多种类型转换成通知
 */
public class ConvertRemindUtil {

    /**
     * 将点赞类转换通知
     *
     * @param thumb 点赞类
     * @return 一般通知类
     */
    public static EventRemindBo convertThumbNotification(Thumb thumb, String postTitle) {
        EventRemindBo eventRemind = new EventRemindBo();
        eventRemind.setActionType(NotificationConstant.REMIND_THUMB_ACTION_TYPE);
        eventRemind.setSourceId(thumb.getPostId());
        if (thumb.getType() == PostConstant.THUMB_COMMENT_TYPE) {
            eventRemind.setSourceType(NotificationConstant.REMIND_SOURCE_COMMENT_TYPE);
        } else if (thumb.getType() == PostConstant.THUMB_POST_TYPE) {
            eventRemind.setSourceType(NotificationConstant.REMIND_SOURCE_POST_TYPE);
        } else if (thumb.getType() == PostConstant.THUMB_REPLY_TYPE) {
            eventRemind.setSourceType(NotificationConstant.REMIND_SOURCE_REPLY_TYPE);
        }
        eventRemind.setSourceTitle(postTitle);
        eventRemind.setSenderId(thumb.getMemberId());
        eventRemind.setRecipientId(thumb.getToMemberId());
        return eventRemind;
    }

    /**
     * 将评论类转换通知
     *
     * @param comment 评论类
     * @return 一般通知类
     */
    public static EventRemindBo convertCommentNotification(Comment comment, String postTitle) {
        EventRemindBo eventRemind = new EventRemindBo();
        eventRemind.setActionType(NotificationConstant.REMIND_COMMENT_ACTION_TYPE);
        eventRemind.setSourceId(comment.getPostId());
        if (comment.getType() == PostConstant.COMMENT_TYPE) {
            eventRemind.setSourceType(NotificationConstant.REMIND_SOURCE_COMMENT_TYPE);
        } else if (comment.getType() == PostConstant.REPLY_TYPE) {
            eventRemind.setSourceType(NotificationConstant.REMIND_SOURCE_REPLY_TYPE);
        }
        eventRemind.setSourceContent(comment.getContent());
        eventRemind.setSourceTitle(postTitle);
        eventRemind.setSenderId(comment.getMemberId());
        eventRemind.setRecipientId(comment.getToMemberId());
        return eventRemind;
    }


}
