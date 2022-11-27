package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.model.dto.CommentDTO;
import cn.goroute.smart.post.model.qo.CommentQO;
import cn.goroute.smart.post.model.vo.CommentVO;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

/**
* @author Alickx
* @description 针对表【comment(文章回复表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface CommentService extends ExtendService<Comment> {

    R<PageResult<CommentDTO>> commentPage(PageParam pageParam, CommentQO commentQO);

	R<Boolean> commentSave(CommentVO commentVO);

	R<Boolean> commentDelete(CommentVO commentVO);
}
