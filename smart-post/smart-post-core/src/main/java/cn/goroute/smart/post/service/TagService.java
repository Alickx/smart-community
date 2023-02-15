package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.entity.TagEntity;
import com.hccake.extend.mybatis.plus.service.ExtendService;

import java.util.List;

/**
* @author Alickx
* @description 针对表【tag(标签表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface TagService extends ExtendService<TagEntity> {


	TagEntity queryByName(String tagName);

	TagEntity queryById(Long tagId);

	List<TagEntity> getTagList();

}
