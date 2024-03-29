package cn.goroute.smart.post.modules.article.mapper;

import cn.goroute.smart.post.domain.ExpandInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Alickx
* @description 针对表【t_post_expand_info(文章拓展信息)】的数据库操作Mapper
* @createDate 2023-03-04 11:12:47
* @Entity cn.goroute.smart.post.domain.PostExpandInfoEntity
*/
public interface PostExpandInfoEntityMapper extends BaseMapper<ExpandInfoEntity> {

	void updateIncrPostCollectCount(@Param("postId") Long postId, @Param("incrCount") Integer incrCount);
}




