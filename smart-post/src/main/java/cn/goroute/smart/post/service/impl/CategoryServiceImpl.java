package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.common.dao.CategoryDao;
import cn.goroute.smart.common.entity.pojo.Category;
import cn.goroute.smart.post.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * @author Alickx
 */
@Service("sectionService")
    public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {


}