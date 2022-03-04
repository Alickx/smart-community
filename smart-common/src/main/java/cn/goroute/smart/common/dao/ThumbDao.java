package cn.goroute.smart.common.dao;

import cn.goroute.smart.common.entity.ThumbEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 点赞记录表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-26 16:17:45
 */
@Mapper
public interface ThumbDao extends BaseMapper<ThumbEntity> {
	
}
