package cn.goroute.smart.user.modules.collect.mapper;

import cn.goroute.smart.user.domain.entity.UserCollectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* @author caiguopeng
* @description 针对表【user_collect(用户收藏表)】的数据库操作Mapper
* @createDate 2022-09-17 19:30:44
* @Entity cn.goroute.smart.user.domain.entity.UserCollect
*/
public interface UserCollectMapper extends BaseMapper<UserCollectEntity> {

	IPage<UserCollectEntity> queryPage(IPage<UserCollectEntity> prodPage);
}




