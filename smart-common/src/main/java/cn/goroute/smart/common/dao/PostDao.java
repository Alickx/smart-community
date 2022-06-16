package cn.goroute.smart.common.dao;

import cn.goroute.smart.common.entity.pojo.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@Mapper
public interface PostDao extends BaseMapper<Post> {

    IPage<Post> getPostBySectionTag(IPage<?> page,
                                    @Param("order") String field,
                                    @Param("sectionUid") Integer sectionUid,
                                    @Param("tagUid") Integer tagUid,
                                    @Param("publish") String isPublish,
                                    @Param("status") Integer status);

    List<Post> getPost();
}
