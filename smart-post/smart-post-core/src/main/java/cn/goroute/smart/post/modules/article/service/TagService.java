package cn.goroute.smart.post.modules.article.service;

import cn.goroute.smart.post.domain.entity.TagEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Alickx
* @description 针对表【tag(标签表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface TagService extends IService<TagEntity> {


	TagEntity queryByName(String tagName);

	TagEntity queryById(Long tagId);

	List<TagEntity> getTagList();

}
