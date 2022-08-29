package cn.goroute.smart.post.mapper;

import cn.goroute.smart.post.entity.pojo.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 文章表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

    IPage<Post> getPostBySectionTag(IPage<?> page,
                                    @Param("order") String field,
                                    @Param("sectionUid") Integer sectionUid,
                                    @Param("tagUid") Integer tagUid,
                                    @Param("publish") String isPublish,
                                    @Param("status") Integer status);

    int getCommentCount(@Param("postUid") Long postUid);

    int selectThumbCount(@Param("postUid") Long postUid);
}
