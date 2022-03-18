package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.common.dao.SectionTagDao;
import cn.goroute.smart.common.dao.TagDao;
import cn.goroute.smart.common.entity.pojo.SectionTag;
import cn.goroute.smart.common.entity.pojo.TagEntity;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.service.SectionTagService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author Alickx
* @description 针对表【t_section_tag(分类标签关联表)】的数据库操作Service实现
* @createDate 2022-03-04 16:06:54
*/
@Service
public class SectionTagServiceImpl extends ServiceImpl<SectionTagDao, SectionTag>
    implements SectionTagService{

    @Autowired
    SectionTagDao sectionTagDao;

    @Autowired
    TagDao tagDao;


    @Override
    public Result getTagBySection(Long sectionUid) {

        List<SectionTag> sectionTagList = sectionTagDao
                .selectList(new QueryWrapper<SectionTag>()
                        .eq("section_uid", sectionUid));

        if (CollectionUtil.isEmpty(sectionTagList)) {
            return Result.error("没有此分类标签数据");
        }

        List<Integer> tagIds = sectionTagList.stream()
                .map(SectionTag::getTagUid).collect(Collectors.toList());

        if (CollectionUtil.isEmpty(tagIds)) {
            return Result.error("该分类下没有标签");
        }

        List<Map<String,Object>> tagContentList = new ArrayList<>();
        tagIds.forEach( t -> {
            TagEntity tagEntity = tagDao.selectById(t);
            Map<String,Object> map = new HashMap<>();
            map.put("label",tagEntity.getContent());
            map.put("value",tagEntity.getUid());
            tagContentList.add(map);
        });

        return Result.ok().put("data",tagContentList);

    }
}




