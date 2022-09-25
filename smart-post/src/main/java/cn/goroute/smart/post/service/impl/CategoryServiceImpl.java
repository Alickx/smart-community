package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.Category;
import cn.goroute.smart.post.mapper.CategoryMapper;
import cn.goroute.smart.post.service.CategoryService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【category(板块表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
public class CategoryServiceImpl extends ExtendServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




