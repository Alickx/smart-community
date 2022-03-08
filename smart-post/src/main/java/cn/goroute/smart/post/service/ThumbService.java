package cn.goroute.smart.post.service;


import cn.goroute.smart.common.entity.CommentVo;
import cn.goroute.smart.common.utils.R;

public interface ThumbService {

    R thumbSave(CommentVo thumbVo);

    R thumbCancel(CommentVo thumbVo);

    int trans();
}
