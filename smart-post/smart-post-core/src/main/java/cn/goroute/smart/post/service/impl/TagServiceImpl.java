package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.domain.entity.TagEntity;
import cn.goroute.smart.post.mapper.TagMapper;
import cn.goroute.smart.post.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Alickx
* @description 针对表【tag(标签表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity>
    implements TagService{

	private final RedisUtil redisUtil;

	@Override
	public TagEntity queryByName(String tagName) {

		List<TagEntity> tagEntities = this.getTagList();

		for (TagEntity tagEntity : tagEntities) {
			if (tagEntity.getContent().equals(tagName)) {
				return tagEntity;
			}
		}

		return null;
	}

	@Override
	public TagEntity queryById(Long tagId) {

		List<TagEntity> tagEntities = this.getTagList();

		for (TagEntity tagEntity : tagEntities) {
			if (tagEntity.getId().equals(tagId)) {
				return tagEntity;
			}
		}

		return null;

	}

	@Override
//	@Cached(key = PostRedisConstant.TagKey.POST_TAG_KEY, ttl = 60 * 60 * 24)
	public List<TagEntity> getTagList() {
		return this.list();
	}


}




