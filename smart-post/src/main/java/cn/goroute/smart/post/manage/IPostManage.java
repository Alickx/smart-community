package cn.goroute.smart.post.manage;

/**
 * @Author: Alickx
 * @Date: 2022/06/08/8:06
 * @Description: manage层接口
 */
public interface IPostManage {

    boolean checkIsThumbOrCollect(Long uid, Long loginUid, int type);

    int getThumbCount(Long postUid);

    int getCommentCount(Long postUid);



}
