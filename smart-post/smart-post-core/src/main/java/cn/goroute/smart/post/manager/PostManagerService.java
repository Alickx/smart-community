package cn.goroute.smart.post.manager;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.post.constant.UserInteractTypeEnum;
import cn.goroute.smart.post.converter.CategoryConverter;
import cn.goroute.smart.post.converter.PostConverter;
import cn.goroute.smart.post.converter.TagConverter;
import cn.goroute.smart.post.domain.Category;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.domain.Tag;
import cn.goroute.smart.post.domain.UserInteract;
import cn.goroute.smart.post.feign.FeignUserProfileService;
import cn.goroute.smart.post.mapper.CategoryMapper;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.mapper.TagMapper;
import cn.goroute.smart.post.model.dto.ContentExpansionDTO;
import cn.goroute.smart.post.model.dto.PostBaseDTO;
import cn.goroute.smart.post.mq.PostSyncEventMessageTemplate;
import cn.goroute.smart.post.service.CategoryService;
import cn.goroute.smart.post.service.TagService;
import cn.goroute.smart.post.service.UserInteractService;
import cn.goroute.smart.search.model.index.PostIndex;
import cn.goroute.smart.user.model.dto.UserProfileDTO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
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
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
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

        // 查找缓存
        String tagList = redisUtil.get(PostConstant.Tag.POST_TAG_KEY);

        List<Tag> tags = JSON.parseArray(tagList, Tag.class);

        if (CollUtil.isEmpty(tags)) {
            // 缓存中没有，从数据库中查找
            tags = tagMapper.selectList(null);
            // 放入缓存
            redisUtil.set(PostConstant.Tag.POST_TAG_KEY, JSON.toJSONString(tags));
        }

        Map<Long, Tag> tagMap = new HashMap<>(tags.size());
        tags.forEach(tag -> tagMap.put(tag.getId(), tag));

        records.forEach(record -> {
            Long tagId = record.getTagId();
            Tag tag = tagMap.get(tagId);
            if (tag != null) {
                record.setTag(TagConverter.INSTANCE.poToDTO(tag));
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

        // 查找缓存
        String categoryList = redisUtil.get(PostConstant.category.POST_CATEGORY_KEY);

        List<Category> categories = JSON.parseArray(categoryList, Category.class);

        if (CollUtil.isEmpty(categories)) {
            // 缓存中没有，从数据库中查找
            categories = categoryMapper.selectList(null);
            // 放入缓存
            redisUtil.set(PostConstant.category.POST_CATEGORY_KEY, JSON.toJSONString(categories));
        }

        Map<Long, Category> categoryMap = new HashMap<>(categories.size());
        categories.forEach(category -> categoryMap.put(category.getId(), category));

        records.forEach(record -> {
            Long categoryId = record.getCategoryId();
            Category category = categoryMap.get(categoryId);
            if (category != null) {
                record.setCategory(CategoryConverter.INSTANCE.poToDTO(category));
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
		List<UserInteract> userInteractList = userInteractService.batchGetUserPostInteract(postIds, UserInteractTypeEnum.POST.getCode(), userId);

		Map<Long,UserInteract> userInteractMap = new HashMap<>(userInteractList.size());
		for (UserInteract userInteract : userInteractList) {
			userInteractMap.put(userInteract.getTargetId(), userInteract);
		}

		for (PostBaseDTO record : records) {
			UserInteract userInteract = userInteractMap.get(record.getId());
			ContentExpansionDTO contentExpansionDTO = ContentExpansionDTO.create();
			if (userInteract != null ) {
				contentExpansionDTO.setIsAuthor(userId.equals(record.getAuthorId()));
				contentExpansionDTO.setIsThumb(userInteract.getIsThumb() == BooleanEnum.TRUE.getValue());
				contentExpansionDTO.setIsCollect(userInteract.getIsCollect() == BooleanEnum.TRUE.getValue());
				contentExpansionDTO.setIsComment(userInteract.getIsComment() == BooleanEnum.TRUE.getValue());
				record.setExpansion(contentExpansionDTO);
			} else {
				record.setExpansion(contentExpansionDTO);
			}
		}

	}

    /**
     * 保存文章到数据库
     *
     * @param post 文章实体类
     */
    @Transactional(rollbackFor = Exception.class)
    public void savePost2Db(Post post) {

        post.setSummary(getPostSummary(post.getContent()));

        postMapper.insert(post);

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

    public void sync2Es(Post postEntity) {

        PostIndex postIndex = PostConverter.INSTANCE.poToPostIndex(postEntity);

        Tag tag = tagService.getById(postEntity.getTagId());
        Category category = categoryService.getById(postEntity.getCategoryId());

        postIndex.setTagName(tag.getContent());
        postIndex.setCategoryName(category.getName());


        postSyncEventMessageTemplate.sendPostMessage(postIndex);

    }
}
