package cn.goroute.smart.post.modules.article.mapper;

import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.qo.PostQO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * @author Alickx
 * @description 针对表【post(文章表)】的数据库操作Mapper
 * @createDate 2022-09-25 16:53:24
 * @Entity cn.goroute.smart.post.domain.Post
 */
public interface PostMapper extends BaseMapper<PostEntity> {

    /**
     * 分页查询
     *
     * @param page   分页参数
     * @param postQO 查询参数对象
     * @return PageResult<PostInfoDTO> 分页数据
     */
    IPage<PostEntity> queryPage(@Param("page") IPage<PostEntity> page,
                                @Param("postQO") PostQO postQO,
                                @Param("status") Integer status,
                                @Param("deleted") Integer deleted);

    /**
     * 递增或递减文章点赞数
     *
     * @param postId     文章id
     * @param thumbNum 点赞数
     */
    void incrThumbCount(@Param("postId") long postId, @Param("thumbNum") int thumbNum);

	/**
	 * 递增或递减文章评论数
	 * @param postId 文章id
	 * @param commentNum 评论数
	 */
    void incrCommentCount(@Param("postId") Long postId, @Param("commentNum") int commentNum);

	/**
	 * 递增或递减文章收藏数
	 * @param postId 文章id
	 * @param collectNum 收藏数
	 */
	void incrCollectCount(@Param("postId") Long postId, @Param("collectNum") int collectNum);

}




