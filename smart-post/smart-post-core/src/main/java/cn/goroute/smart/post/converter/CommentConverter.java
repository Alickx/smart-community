package cn.goroute.smart.post.converter;

import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.model.dto.CommentDTO;
import cn.goroute.smart.post.model.vo.CommentVO;
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
	 * @param comment 评论实体
	 * @return CommentDTO
	 */
	CommentDTO poToDto(Comment comment);

	/**
	 *  VO 转 PO
	 * @param commentVO 评论视图对象
	 * @return Comment
	 */
	Comment voToPo(CommentVO commentVO);

	List<CommentDTO> poToDto(List<Comment> commentList);


}
