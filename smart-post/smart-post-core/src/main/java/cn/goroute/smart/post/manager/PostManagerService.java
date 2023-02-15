package cn.goroute.smart.post.manager;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.enums.UserInteractTypeEnum;
import cn.goroute.smart.post.converter.CategoryConverter;
import cn.goroute.smart.post.converter.TagConverter;
import cn.goroute.smart.post.domain.entity.CategoryEntity;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.entity.TagEntity;
import cn.goroute.smart.post.domain.entity.UserInteractEntity;
import cn.goroute.smart.post.feign.FeignUserProfileService;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.domain.dto.ContentExpansionDTO;
import cn.goroute.smart.post.domain.dto.PostBaseDTO;
import cn.goroute.smart.post.mq.PostSyncEventMessageTemplate;
import cn.goroute.smart.post.service.CategoryService;
import cn.goroute.smart.post.service.TagService;
import cn.goroute.smart.post.service.UserInteractService;
import cn.goroute.smart.user.model.dto.UserProfileDTO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hccake.ballcat.common.core.constant.enums.BooleanEnum;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.model.result.SystemResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/11:01
 * @Description: 文章服务
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PostManagerService {

    private final FeignUserProfileService feignUserProfileService;
    private final RedisUtil redisUtil;
    private final PostMapper postMapper;
    private final PostSyncEventMessageTemplate postSyncEventMessageTemplate;
    private final TagService tagService;
    private final CategoryService categoryService;
	private final UserInteractService userInteractService;

	/**
     * 补充文章作者，板块和标签信息
     *
     * @param records 文章列表
     * @return 补充后的文章列表
     */
    public List<? extends PostBaseDTO> fillInfo(List<? extends PostBaseDTO> records) {

        Long userId = StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : null;

		// 补充作者信息
		fillAuthor(records);

		// 补充板块信息
		fillCategory(records);

		// 补充点赞信息和收藏信息
		fillExpansion(records,userId);

		// 补充标签信息
		fillTag(records);


        return records;
    }

    /**
     * 补充文章标签信息
     *
     * @param records 文章列表
     */
    private void fillTag(List<? extends PostBaseDTO> records) {

		List<TagEntity> tagEntities = tagService.getTagList();

		Map<String, TagEntity> tagMap = new HashMap<>(tagEntities.size());
        tagEntities.forEach(tag -> tagMap.put(tag.getContent(), tag));

        records.forEach(record -> {
			String tagName = record.getTagName();
			TagEntity tagEntity = tagMap.get(tagName);
            if (tagEntity != null) {
                record.setTag(TagConverter.INSTANCE.poToDTO(tagEntity));
            }
        });


    }

    /**
     * 补充文章作者信息
     *
     * @param records 文章列表
     */
    private void fillAuthor(List<? extends PostBaseDTO> records) {
        if (CollUtil.isNotEmpty(records)) {
            List<Long> userIds = records.stream().map(PostBaseDTO::getAuthorId).distinct().toList();
            R<List<UserProfileDTO>> resp;
            try {
                resp = feignUserProfileService.batchGetUserProfile(userIds);
            } catch (Exception e) {
                log.error("获取用户信息失败,userIds: [{}],调用用户服务超时或失败", userIds);
                return;
            }
            if (resp.getCode() == SystemResultCode.SUCCESS.getCode() && resp.getData() != null) {
                Map<Long, UserProfileDTO> userProfileMap = new HashMap<>();
                for (UserProfileDTO userProfileDTO : resp.getData()) {
                    userProfileMap.put(userProfileDTO.getUserId(), userProfileDTO);
                }
                for (PostBaseDTO postBaseDTO : records) {
                    postBaseDTO.setAuthor(userProfileMap.get(postBaseDTO.getAuthorId()));
                }
            }
        }
    }

    /**
     * 补充文章板块信息
     *
     * @param records 文章列表
     */
    private void fillCategory(List<? extends PostBaseDTO> records) {

		List<CategoryEntity> categories = categoryService.getCategoryList();

		Map<String, CategoryEntity> categoryMap = new HashMap<>(categories.size());
        categories.forEach(category -> categoryMap.put(category.getName(), category));

        records.forEach(record -> {
			String categoryName = record.getCategoryName();
			CategoryEntity categoryEntity = categoryMap.get(categoryName);
            if (categoryEntity != null) {
                record.setCategory(CategoryConverter.INSTANCE.poToDTO(categoryEntity));
            }
        });

    }

    /**
     * 补充文章拓展信息
     *
     * @param records 文章列表
     */
    private void fillExpansion(List<? extends PostBaseDTO> records,Long userId) {

		if (CollUtil.isEmpty(records)) {
			return;
		}

		if (null == userId) {
			// 查询的用户为游客
			for (PostBaseDTO record : records) {
				ContentExpansionDTO contentExpansionDTO = ContentExpansionDTO.create();
				record.setExpansion(contentExpansionDTO);
			}
			return;
		}


		List<Long> postIds = records.stream().map(PostBaseDTO::getId).toList();

		// 调用用户关系表
		List<UserInteractEntity> userInteractEntityList = userInteractService.batchGetUserPostInteract(postIds, UserInteractTypeEnum.POST.getCode(), userId);

		Map<Long, UserInteractEntity> userInteractMap = new HashMap<>(userInteractEntityList.size());
		for (UserInteractEntity userInteractEntity : userInteractEntityList) {
			userInteractMap.put(userInteractEntity.getTargetId(), userInteractEntity);
		}

		for (PostBaseDTO record : records) {
			UserInteractEntity userInteractEntity = userInteractMap.get(record.getId());
			ContentExpansionDTO contentExpansionDTO = ContentExpansionDTO.create();
			if (userInteractEntity != null ) {
				contentExpansionDTO.setIsAuthor(userId.equals(record.getAuthorId()));
				contentExpansionDTO.setIsThumb(userInteractEntity.getIsThumb() == BooleanEnum.TRUE.getValue());
				contentExpansionDTO.setIsCollect(userInteractEntity.getIsCollect() == BooleanEnum.TRUE.getValue());
				contentExpansionDTO.setIsComment(userInteractEntity.getIsComment() == BooleanEnum.TRUE.getValue());
				record.setExpansion(contentExpansionDTO);
			} else {
				record.setExpansion(contentExpansionDTO);
			}
		}

	}

    /**
     * 保存文章到数据库
     *
     * @param postEntity 文章实体类
     */
    @Transactional(rollbackFor = Exception.class)
    public void savePost2Db(PostEntity postEntity) {

        postEntity.setSummary(getPostSummary(postEntity.getContent()));

        postMapper.insert(postEntity);

    }

    private String getPostSummary(String content) {
        String summary = "";
        if (StrUtil.isNotBlank(content)) {
            summary = Jsoup.parse(content).text();
            if (summary.length() > 100) {
                summary = summary.substring(0, 100) + "...";
            }
        }
        return summary;
    }
}
