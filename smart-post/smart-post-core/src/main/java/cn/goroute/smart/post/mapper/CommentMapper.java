package cn.goroute.smart.post.mapper;

import cn.goroute.smart.common.constant.StatusConstant;
import cn.goroute.smart.post.converter.CommentConverter;
import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.post.domain.vo.CommentVO;
import cn.goroute.smart.post.domain.qo.CommentQO;
import cn.goroute.smart.post.domain.qo.PostQO;
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
public interface CommentMapper extends ExtendMapper<CommentEntity> {

	default PageResult<CommentVO> queryPage(PageParam pageParam, CommentQO commentQO) {
		IPage<CommentEntity> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<CommentEntity> wrapper = new LambdaQueryWrapperX<>(CommentEntity.class);
		wrapper.eqIfPresent(CommentEntity::getUserId, commentQO.getUserId())
				.eqIfPresent(CommentEntity::getPostId, commentQO.getPostId())
				.eqIfPresent(CommentEntity::getType, commentQO.getType())
				.eqIfPresent(CommentEntity::getState, StatusConstant.NORMAL_STATUS)
				.eqIfPresent(CommentEntity::getDeleted, BooleanEnum.FALSE.getValue())
				.eqIfPresent(CommentEntity::getFirstCommentId, commentQO.getFirstCommentId());
		IPage<CommentEntity> commentIPage = this.selectPage(page, wrapper);
		IPage<CommentVO> convert = commentIPage.convert(CommentConverter.INSTANCE::poToVO);
		return new PageResult<>(convert.getRecords(),convert.getTotal());
	}

	default PageResult<CommentVO> queryPageReplyList(PageParam pageParam, Long postId, Long firstCommentId) {
		IPage<CommentEntity> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<CommentEntity> wrapper = new LambdaQueryWrapperX<>(CommentEntity.class);
		wrapper.eqIfPresent(CommentEntity::getPostId, postId)
				.eqIfPresent(CommentEntity::getFirstCommentId, firstCommentId)
				.eqIfPresent(CommentEntity::getState, StatusConstant.NORMAL_STATUS)
				.eqIfPresent(CommentEntity::getDeleted, BooleanEnum.FALSE.getValue());
		IPage<CommentEntity> commentIPage = this.selectPage(page, wrapper);
		IPage<CommentVO> convert = commentIPage.convert(CommentConverter.INSTANCE::poToVO);
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

	default List<CommentVO> queryMoreReply(CommentQO commentQO) {
		LambdaQueryWrapperX<CommentEntity> wrapper = new LambdaQueryWrapperX<>(CommentEntity.class);
		wrapper.eqIfPresent(CommentEntity::getPostId, commentQO.getPostId())
				.eqIfPresent(CommentEntity::getFirstCommentId, commentQO.getFirstCommentId())
				.eqIfPresent(CommentEntity::getState, StatusConstant.NORMAL_STATUS)
				.eqIfPresent(CommentEntity::getDeleted, BooleanEnum.FALSE.getValue());
		List<CommentEntity> commentEntities = this.selectList(wrapper);
		return CommentConverter.INSTANCE.poToDto(commentEntities);
	}

	default PageResult<Long> queryPostIdsByComment(PageParam pageParam, PostQO postQO) {
		IPage<CommentEntity> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<CommentEntity> wrapper = new LambdaQueryWrapperX<>(CommentEntity.class);
		wrapper.eqIfPresent(CommentEntity::getUserId, postQO.getUserId())
				.eqIfPresent(CommentEntity::getState, StatusConstant.NORMAL_STATUS)
				.groupBy(CommentEntity::getId, CommentEntity::getPostId);
		IPage<CommentEntity> commentIPage = this.selectPage(page, wrapper);
		IPage<Long> convert = commentIPage.convert(CommentEntity::getPostId);
		return new PageResult<>(convert.getRecords(),convert.getTotal());
	}
}




