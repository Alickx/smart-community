package cn.goroute.smart.post.mapper;

import cn.goroute.smart.post.domain.entity.ThumbEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Alickx
 * @description 针对表【thumb(点赞表)】的数据库操作Mapper
 * @createDate 2022-09-25 16:53:24
 * @Entity cn.goroute.smart.post.domain.Thumb
 */
public interface ThumbMapper extends BaseMapper<ThumbEntity> {

    /**
     * 通过用户id和目标id查找是否存在点赞记录
     *
     * @param userId 用户id
     * @param toId   目标id
     * @param type   点赞类型
     * @return 存在返回实体类，不存在返回null
     */
    ThumbEntity selectByUserIdAndToIdAndType(@Param("userId") Long userId, @Param("toId") Long toId, @Param("type") Integer type);


}




