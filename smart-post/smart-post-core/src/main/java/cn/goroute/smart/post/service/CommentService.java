package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.post.domain.vo.CommentVO;
import cn.goroute.smart.post.domain.qo.CommentQO;
import cn.goroute.smart.post.domain.qo.PostQO;
import cn.goroute.smart.post.domain.form.CommentForm;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

import java.util.List;

/**
* @author Alickx
* @description 针对表【comment(文章回复表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface CommentService extends ExtendService<CommentEntity> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param commentQO 查询参数
	 * @return 分页结果
	 */
    R<PageResult<CommentVO>> queryPage(PageParam pageParam, CommentQO commentQO);

	/**
	 * 保存评论/回复
	 * @param commentForm 评论/回复信息
	 * @return 保存结果
	 */
	R<Long> commentSave(CommentForm commentForm);

	/**
	 * 删除评论/回复
	 * @param commentForm 评论/回复信息
	 * @return 删除结果
	 */
	R<Boolean> commentDelete(CommentForm commentForm);

	R<List<CommentVO>> queryMoreReply(CommentQO commentQO);

	PageResult<Long> queryPostIdsByComment(PageParam pageParam, PostQO postQO);
}
