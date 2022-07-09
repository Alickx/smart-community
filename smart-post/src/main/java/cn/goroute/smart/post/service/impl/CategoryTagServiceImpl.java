package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.common.dao.CategoryDao;
import cn.goroute.smart.common.dao.CategoryTagDao;
import cn.goroute.smart.common.entity.dto.CategoryTagDto;
import cn.goroute.smart.common.entity.pojo.CategoryTag;
import cn.goroute.smart.common.entity.pojo.Tag;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.service.CategoryTagService;
import cn.goroute.smart.post.service.TagService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CategoryTagServiceImpl extends ServiceImpl<CategoryTagDao, CategoryTag>
    implements CategoryTagService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryTagDao categoryTagDao;

    @Autowired
    TagService tagService;

    @Autowired
    RedisUtil redisUtil;

    /**
     * @description 根据分类id查询标签
     * @param categoryUid 分类id
     * @return 标签列表
     */
    @Override
    public Result getTagByCategory(Long categoryUid) {

        List<CategoryTag> categoryTagList = categoryTagDao
                .selectList(new LambdaQueryWrapper<CategoryTag>()
                        .eq(CategoryTag::getCategoryUid, categoryUid));

        if (CollectionUtil.isEmpty(categoryTagList)) {
            return Result.error("没有此分类标签数据");
        }

        List<Long> tagIds = categoryTagList.stream()
                .map(CategoryTag::getTagUid).distinct().collect(Collectors.toList());

        if (CollectionUtil.isEmpty(tagIds)) {
            return Result.error("该分类下没有标签");
        }

        List<Map<String,Object>> tagContentList = new ArrayList<>(16);

        List<Tag> tagList = tagService.listByIds(tagIds);
        tagList.parallelStream().forEach(t -> {
            Map<String,Object> map = new HashMap<>(2);
            map.put("label", t.getContent());
            map.put("value", t.getUid());
            tagContentList.add(map);
        });



        return Result.ok().put("data",tagContentList);

    }

    /**
     * @description 查询获取所有分类标签
     * @return
     */
    @Override
    public Result getCategoryTagAll() {

        List<CategoryTagDto> categoryTagDtoList = (List<CategoryTagDto>) redisUtil.get("category");

        if (CollectionUtil.isEmpty(categoryTagDtoList)) {
            return Result.error("没有分类标签数据");
        }

        return Result.ok().put("data", categoryTagDtoList);
    }


}




