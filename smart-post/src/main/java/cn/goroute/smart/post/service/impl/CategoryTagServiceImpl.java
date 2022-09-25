package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.CategoryTag;
import cn.goroute.smart.post.mapper.CategoryTagMapper;
import cn.goroute.smart.post.service.CategoryTagService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【category_tag(分类标签关联表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
public class CategoryTagServiceImpl extends ExtendServiceImpl<CategoryTagMapper, CategoryTag>
    implements CategoryTagService{

}




