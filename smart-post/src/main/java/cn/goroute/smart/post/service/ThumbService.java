package cn.goroute.smart.post.service;


import cn.goroute.smart.common.entity.pojo.Comment;
import cn.goroute.smart.common.entity.vo.CommentVO;
import cn.goroute.smart.common.entity.vo.PostQueryListVO;
import cn.goroute.smart.common.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ThumbService extends IService<Comment> {

    Result thumbSave(CommentVO thumbVo);

    Result thumbCancel(CommentVO thumbVo);

    int trans();

    Result listByMemberUid(PostQueryListVO postQueryListVO);

    int transCount();
}
