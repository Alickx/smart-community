package cn.goroute.smart.post.manage;

import cn.goroute.smart.common.entity.pojo.Post;

/**
 * @Author: Alickx
 * @Date: 2022/06/08/8:06
 * @Description: manage层接口
 */
public interface IPostManage {

    boolean checkIsThumbOrCollect(Long uid, Long loginUid, int type);

    int getThumbCount(Post post);

    int getCommentCount(Post post);



}
