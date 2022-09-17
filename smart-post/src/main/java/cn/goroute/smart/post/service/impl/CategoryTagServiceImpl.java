package cn.goroute.smart.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.post.domain.CategoryTag;
import cn.goroute.smart.post.service.CategoryTagService;
import cn.goroute.smart.post.mapper.CategoryTagMapper;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【category_tag(分类标签关联表)】的数据库操作Service实现
* @createDate 2022-09-17 19:31:22
*/
@Service
public class CategoryTagServiceImpl extends ServiceImpl<CategoryTagMapper, CategoryTag>
    implements CategoryTagService{

}




