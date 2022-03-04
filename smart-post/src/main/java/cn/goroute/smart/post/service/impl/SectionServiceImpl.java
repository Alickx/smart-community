package cn.goroute.smart.post.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;

import cn.goroute.smart.post.dao.SectionDao;
import cn.goroute.smart.common.entity.SectionEntity;
import cn.goroute.smart.post.service.SectionService;


@Service("sectionService")
public class SectionServiceImpl extends ServiceImpl<SectionDao, SectionEntity> implements SectionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SectionEntity> page = this.page(
                new Query<SectionEntity>().getPage(params),
                new QueryWrapper<SectionEntity>()
        );

        return new PageUtils(page);
    }

}