package cn.goroute.smart.post.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;

import cn.goroute.smart.post.dao.TagDao;
import cn.goroute.smart.common.entity.TagEntity;
import cn.goroute.smart.post.service.TagService;


@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TagEntity> page = this.page(
                new Query<TagEntity>().getPage(params),
                new QueryWrapper<TagEntity>()
        );

        return new PageUtils(page);
    }

}