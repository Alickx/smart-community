package cn.goroute.smart.post.mapper;

import cn.goroute.smart.post.converter.CommentConverter;
import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.model.dto.CommentDTO;
import cn.goroute.smart.post.model.qo.CommentQO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hccake.ballcat.common.core.constant.enums.BooleanEnum;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.extend.mybatis.plus.conditions.query.LambdaQueryWrapperX;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;
import org.apache.ibatis.annotations.Param;

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
				.eqIfPresent(Comment::getState,0)
				.eqIfPresent(Comment::getDeleted, BooleanEnum.FALSE.getValue())
				.eqIfPresent(Comment::getFirstCommentId, commentQO.getFirstCommentId());
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
}




