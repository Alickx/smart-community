package cn.goroute.smart.post.modules.comment.converter.converter;

import cn.goroute.smart.post.domain.dto.CommentDTO;
import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.post.domain.form.CommentForm;
import cn.goroute.smart.post.domain.vo.CommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/11/27/13:18
 * @Description:
 */
@Mapper
public interface CommentConverter {

	CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);

	/**
	 * PO 转 DTO
	 * @param commentEntity 评论实体
	 * @return CommentDTO
	 */
	CommentDTO poToDto(CommentEntity commentEntity);


	List<CommentVO> poToDto(List<CommentEntity> commentEntityList);

	CommentVO poToVO(CommentEntity commentEntity);

	List<CommentVO> poToVO(List<CommentEntity> commentEntityList);


	CommentEntity formToPo(CommentForm commentForm);
}
