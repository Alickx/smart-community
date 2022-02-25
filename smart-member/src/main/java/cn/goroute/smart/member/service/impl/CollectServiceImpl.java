package cn.goroute.smart.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;

import cn.goroute.smart.member.dao.CollectDao;
import cn.goroute.smart.member.entity.CollectEntity;
import cn.goroute.smart.member.service.CollectService;


@Service("collectService")
public class CollectServiceImpl extends ServiceImpl<CollectDao, CollectEntity> implements CollectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CollectEntity> page = this.page(
                new Query<CollectEntity>().getPage(params),
                new QueryWrapper<CollectEntity>()
        );

        return new PageUtils(page);
    }

}