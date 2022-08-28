package cn.goroute.smart.post;

import cn.goroute.smart.post.mapper.CategoryMapper;
import cn.goroute.smart.post.mapper.CategoryTagMapper;
import cn.goroute.smart.post.mapper.TagMapper;
import cn.goroute.smart.common.entity.dto.CategoryTagDto;
import cn.goroute.smart.common.entity.pojo.Category;
import cn.goroute.smart.common.entity.pojo.CategoryTag;
import cn.goroute.smart.common.entity.pojo.Tag;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alickx
 * @Date: 2022/07/09 11:13
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@RefreshScope
@MapperScan("cn.goroute.smart.common.dao")
@EnableFeignClients(basePackages = {"cn.goroute.smart.post.feign","cn.goroute.smart.common.feign"})
public class SmartPostApplication {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryTagMapper categoryTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    RedisUtil redisUtil;

    public static void main(String[] args) {
        SpringApplication.run(SmartPostApplication.class, args);
    }


    @PostConstruct
    public void init() {
        // 初始化分类标签数据
        initCategoryTag();
    }

    private void initCategoryTag() {

        List<Category> categories = categoryMapper.selectList(null);

        List<CategoryTagDto> result = new ArrayList<>();

        for (Category category : categories) {

            List<CategoryTag> categoryTags = categoryTagMapper.selectList(new LambdaQueryWrapper<CategoryTag>()
                    .eq(CategoryTag::getCategoryUid, category.getUid()));

            if (CollectionUtil.isEmpty(categoryTags)) {
                continue;
            }

            List<Long> tagUids = categoryTags.stream().map(CategoryTag::getTagUid).collect(Collectors.toList());

            List<Tag> tags = tagMapper.selectBatchIds(tagUids);

            CategoryTagDto categoryTagDTO = new CategoryTagDto();
            BeanUtils.copyProperties(category, categoryTagDTO);
            categoryTagDTO.setTags(tags);
            result.add(categoryTagDTO);
        }

        redisUtil.set("category", result);
    }

}
