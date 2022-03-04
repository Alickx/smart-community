package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.common.dao.ThumbDao;
import cn.goroute.smart.common.entity.ThumbEntity;
import cn.goroute.smart.member.service.ThumbService;


@Service("thumbService")
public class ThumbServiceImpl extends ServiceImpl<ThumbDao, ThumbEntity> implements ThumbService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ThumbEntity> page = this.page(
                new Query<ThumbEntity>().getPage(params),
                new QueryWrapper<ThumbEntity>()
        );

        return new PageUtils(page);
    }

}