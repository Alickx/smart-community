package cn.goroute.smart.post.modules.comment.mapper;

import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.post.domain.qo.CommentQO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Alickx
 * @description 针对表【comment(文章回复表)】的数据库操作Mapper
 * @createDate 2022-09-25 16:53:24
 * @Entity cn.goroute.smart.post.domain.Comment
 */
public interface CommentMapper extends BaseMapper<CommentEntity> {

    IPage<CommentEntity> queryPage(@Param("page") IPage<CommentEntity> page,
                                   @Param("commentQO") CommentQO commentQO,
                                   @Param("status") Integer status);


    List<CommentEntity> queryMoreReply(@Param("commentQO") CommentQO commentQO,
                                       @Param("status") Integer status);

    /**
     * 递增或递减点赞数
     *
     * @param id       主键id
     * @param thumbNum 点赞数
     */
    void incrThumbNum(@Param("id") Long id, @Param("thumbNum") int thumbNum);

	/**
	 * 获取该用户评论的文章id
	 * @param page 分页
	 * @param userId 用户id
	 * @param status 状态
	 * @return
	 */
    IPage<Long> queryPostIdsByComment(@Param("page") IPage<Long> page,
                                               @Param("userId") Long userId,
                                               @Param("status") Integer status);
}




