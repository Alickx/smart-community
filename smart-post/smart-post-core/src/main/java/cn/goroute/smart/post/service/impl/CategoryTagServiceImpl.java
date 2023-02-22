package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.entity.CategoryTagEntity;
import cn.goroute.smart.post.mapper.CategoryTagMapper;
import cn.goroute.smart.post.service.CategoryTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【category_tag(分类标签关联表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
public class CategoryTagServiceImpl extends ServiceImpl<CategoryTagMapper, CategoryTagEntity>
    implements CategoryTagService{

}




