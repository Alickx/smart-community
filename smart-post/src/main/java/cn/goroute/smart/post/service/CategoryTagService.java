package cn.goroute.smart.post.service;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.post.entity.pojo.CategoryTag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Alickx
* @description 针对表【t_section_tag(分类标签关联表)】的数据库操作Service
* @createDate 2022-03-04 16:06:54
*/
public interface CategoryTagService extends IService<CategoryTag> {

    Response getTagByCategory(Long sectionUid);

    Response getCategoryTagAll();
}
