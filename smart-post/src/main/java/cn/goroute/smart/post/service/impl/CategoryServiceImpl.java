package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.mapper.CategoryMapper;
import cn.goroute.smart.common.entity.pojo.Category;
import cn.goroute.smart.post.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * @author Alickx
 */
@Service("sectionService")
    public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


}