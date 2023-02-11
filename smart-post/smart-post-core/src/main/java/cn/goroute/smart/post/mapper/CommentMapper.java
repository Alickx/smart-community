package cn.goroute.smart.post.mapper;

import cn.goroute.smart.common.constant.StateConstant;
import cn.goroute.smart.post.converter.CommentConverter;
import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.model.dto.CommentDTO;
import cn.goroute.smart.post.model.qo.CommentQO;
import cn.goroute.smart.post.model.qo.PostQO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hccake.ballcat.common.core.constant.enums.BooleanEnum;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.extend.mybatis.plus.conditions.query.LambdaQueryWrapperX;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Alickx
* @description 针对表【comment(文章回复表)】的数据库操作Mapper
* @createDate 2022-09-25 16:53:24
* @Entity cn.goroute.smart.post.domain.Comment
*/
public interface CommentMapper extends ExtendMapper<Comment> {

	default PageResult<CommentDTO> queryPage(PageParam pageParam, CommentQO commentQO) {
		IPage<Comment> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<Comment> wrapper = new LambdaQueryWrapperX<>(Comment.class);
		wrapper.eqIfPresent(Comment::getUserId, commentQO.getUserId())
				.eqIfPresent(Comment::getPostId, commentQO.getPostId())
				.eqIfPresent(Comment::getType, commentQO.getType())
				.eqIfPresent(Comment::getState, StateConstant.NORMAL_STATE)
				.eqIfPresent(Comment::getDeleted, BooleanEnum.FALSE.getValue())
				.eqIfPresent(Comment::getFirstCommentId, commentQO.getFirstCommentId());
		IPage<Comment> commentIPage = this.selectPage(page, wrapper);
		IPage<CommentDTO> convert = commentIPage.convert(CommentConverter.INSTANCE::poToDto);
		return new PageResult<>(convert.getRecords(),convert.getTotal());
	}

	default PageResult<CommentDTO> queryPageReplyList(PageParam pageParam,Long postId,Long firstCommentId) {
		IPage<Comment> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<Comment> wrapper = new LambdaQueryWrapperX<>(Comment.class);
		wrapper.eqIfPresent(Comment::getPostId, postId)
				.eqIfPresent(Comment::getFirstCommentId, firstCommentId)
				.eqIfPresent(Comment::getState, StateConstant.NORMAL_STATE)
				.eqIfPresent(Comment::getDeleted, BooleanEnum.FALSE.getValue());
		IPage<Comment> commentIPage = this.selectPage(page, wrapper);
		IPage<CommentDTO> convert = commentIPage.convert(CommentConverter.INSTANCE::poToDto);
		return new PageResult<>(convert.getRecords(),convert.getTotal());
	}

	/**
	 * 递增点赞数
	 * @param id 主键id
	 * @param thumbNum 点赞数
	 */
    void incrThumbNum(@Param("id") Long id,@Param("thumbNum") int thumbNum);

	/**
	 * 递减点赞数
	 * @param id 主键id
	 * @param thumbNum 点赞数
	 */
	void descThumbNum(@Param("id") Long id,@Param("thumbNum") int thumbNum);

	default List<CommentDTO> queryMoreReply(CommentQO commentQO) {
		LambdaQueryWrapperX<Comment> wrapper = new LambdaQueryWrapperX<>(Comment.class);
		wrapper.eqIfPresent(Comment::getPostId, commentQO.getPostId())
				.eqIfPresent(Comment::getFirstCommentId, commentQO.getFirstCommentId())
				.eqIfPresent(Comment::getState, StateConstant.NORMAL_STATE)
				.eqIfPresent(Comment::getDeleted, BooleanEnum.FALSE.getValue());
		List<Comment> comments = this.selectList(wrapper);
		return CommentConverter.INSTANCE.poToDto(comments);
	}

	default PageResult<Long> queryPostIdsByComment(PageParam pageParam, PostQO postQO) {
		IPage<Comment> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<Comment> wrapper = new LambdaQueryWrapperX<>(Comment.class);
		wrapper.eqIfPresent(Comment::getUserId, postQO.getUserId())
				.eqIfPresent(Comment::getState, StateConstant.NORMAL_STATE)
				.groupBy(Comment::getId,Comment::getPostId);
		IPage<Comment> commentIPage = this.selectPage(page, wrapper);
		IPage<Long> convert = commentIPage.convert(Comment::getPostId);
		return new PageResult<>(convert.getRecords(),convert.getTotal());
	}
}




