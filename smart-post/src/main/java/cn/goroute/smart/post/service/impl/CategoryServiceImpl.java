package cn.goroute.smart.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.post.domain.Category;
import cn.goroute.smart.post.service.CategoryService;
import cn.goroute.smart.post.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【category(板块表)】的数据库操作Service实现
* @createDate 2022-09-17 19:31:22
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




