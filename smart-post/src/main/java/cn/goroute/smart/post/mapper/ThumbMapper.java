package cn.goroute.smart.post.mapper;

import cn.goroute.smart.common.entity.pojo.Thumb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Alickx
* @description 针对表【t_thumb(点赞表)】的数据库操作Mapper
* @createDate 2022-03-23 16:44:34
* @Entity cn.goroute.smart.common.entity.pojo.Thumb
*/
@Mapper
public interface ThumbMapper extends BaseMapper<Thumb> {

    int selectThumbCount(@Param("postUid") Long postUid);
}




