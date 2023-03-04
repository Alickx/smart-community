package cn.goroute.smart.post.modules.article.manager;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.enums.BooleanEnum;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.common.modules.result.SystemResultCode;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.enums.UserInteractTypeEnum;
import cn.goroute.smart.post.domain.PostExpandInfoEntity;
import cn.goroute.smart.post.domain.dto.ContentExpansionDTO;
import cn.goroute.smart.post.domain.entity.CategoryEntity;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.entity.TagEntity;
import cn.goroute.smart.post.domain.entity.UserInteractEntity;
import cn.goroute.smart.post.domain.vo.PostBaseVO;
import cn.goroute.smart.post.feign.FeignUserProfileService;
import cn.goroute.smart.post.modules.article.converter.CategoryConverter;
import cn.goroute.smart.post.modules.article.converter.TagConverter;
import cn.goroute.smart.post.modules.article.mapper.PostMapper;
import cn.goroute.smart.post.modules.article.service.CategoryService;
import cn.goroute.smart.post.modules.article.service.PostExpandInfoEntityService;
import cn.goroute.smart.post.modules.article.service.TagService;
import cn.goroute.smart.post.modules.article.service.UserInteractService;
import cn.goroute.smart.post.util.MarkdownUtil;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private final TagService tagService;
    private final CategoryService categoryService;
    private final UserInteractService userInteractService;
    private final PostExpandInfoEntityService postExpandInfoEntityService;

    /**
     * 补充文章作者，板块和标签信息
     *
     * @param records 文章列表
     * @return 补充后的文章列表
     */
    public List<? extends PostBaseVO> fillInfo(List<? extends PostBaseVO> records) {

        Long userId = StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : null;

        // 补充作者信息
        fillAuthor(records);
        // 补充板块信息
        fillCategory(records);
        // 查询点赞，收藏，浏览量和评论量
        fillInfoExpand(records);
        // 补充用户和文章之间的关系信息
        fillUserInteract(records, userId);
        // 补充标签信息
        fillTag(records);


        return records;
    }

    private void fillInfoExpand(List<? extends PostBaseVO> records) {

        // 批量查询文章拓展信息，点赞量，收藏量，浏览量，评论量
        List<Long> postIds = records.stream().map(PostBaseVO::getId).toList();
        List<PostExpandInfoEntity> postExpandInfoEntities = postExpandInfoEntityService.batchPostExpandInfo(postIds);

        // Map 以 postId为key，PostExpandInfoEntity为value
        Map<Long, PostExpandInfoEntity> postExpandInfoEntityMap = postExpandInfoEntities
                .stream()
                .collect(Collectors.toMap(PostExpandInfoEntity::getPostId, Function.identity()));

        // 遍历填充
        for (PostBaseVO record : records) {
            PostExpandInfoEntity postExpandInfoEntity = postExpandInfoEntityMap.get(record.getId());
            if (postExpandInfoEntity != null) {
                record.setThumbCount(postExpandInfoEntity.getThumbCount());
                record.setCollectCount(postExpandInfoEntity.getCollectCount());
                record.setCommentCount(postExpandInfoEntity.getCommentCount());
                record.setViewCount(postExpandInfoEntity.getViewCount());
            } else {
                record.setThumbCount(0);
                record.setCollectCount(0);
                record.setCommentCount(0);
                record.setViewCount(0);
            }
        }
    }

    /**
     * 补充文章标签信息
     *
     * @param records 文章列表
     */
    private void fillTag(List<? extends PostBaseVO> records) {

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
    private void fillAuthor(List<? extends PostBaseVO> records) {
        if (CollUtil.isNotEmpty(records)) {
            List<Long> userIds = records.stream().map(PostBaseVO::getAuthorId).distinct().toList();
            R<List<UserProfileVO>> resp;
            try {
                resp = feignUserProfileService.batchGetUserProfile(userIds);
            } catch (Exception e) {
                log.error("获取用户信息失败,userIds: [{}],调用用户服务超时或失败", userIds);
                return;
            }
            if (resp.getCode() == SystemResultCode.SUCCESS.getCode() && resp.getData() != null) {
                Map<Long, UserProfileVO> userProfileMap = new HashMap<>();
                for (UserProfileVO userProfileVO : resp.getData()) {
                    userProfileMap.put(userProfileVO.getUserId(), userProfileVO);
                }
                for (PostBaseVO postBaseVO : records) {
                    postBaseVO.setAuthor(userProfileMap.get(postBaseVO.getAuthorId()));
                }
            }
        }
    }

    /**
     * 补充文章板块信息
     *
     * @param records 文章列表
     */
    private void fillCategory(List<? extends PostBaseVO> records) {

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
    private void fillUserInteract(List<? extends PostBaseVO> records, Long userId) {

        if (CollUtil.isEmpty(records)) {
            return;
        }

        if (null == userId) {
            // 查询的用户为游客
            for (PostBaseVO record : records) {
                ContentExpansionDTO contentExpansionDTO = ContentExpansionDTO.create();
                record.setExpansion(contentExpansionDTO);
            }
            return;
        }


        List<Long> postIds = records.stream().map(PostBaseVO::getId).toList();

        // 调用用户关系表
        List<UserInteractEntity> userInteractEntityList = userInteractService.batchGetUserPostInteract(postIds, UserInteractTypeEnum.POST.getCode(), userId);

        Map<Long, UserInteractEntity> userInteractMap = new HashMap<>(userInteractEntityList.size());
        for (UserInteractEntity userInteractEntity : userInteractEntityList) {
            userInteractMap.put(userInteractEntity.getTargetId(), userInteractEntity);
        }

        for (PostBaseVO record : records) {
            UserInteractEntity userInteractEntity = userInteractMap.get(record.getId());
            ContentExpansionDTO contentExpansionDTO = ContentExpansionDTO.create();
            if (userInteractEntity != null) {
                contentExpansionDTO.setIsAuthor(userId.equals(record.getAuthorId()));
                contentExpansionDTO.setIsThumb(userInteractEntity.getIsThumb().equals(BooleanEnum.TRUE.intValue()));
                contentExpansionDTO.setIsCollect(userInteractEntity.getIsCollect().equals(BooleanEnum.TRUE.intValue()));
                contentExpansionDTO.setIsComment(userInteractEntity.getIsComment().equals(BooleanEnum.TRUE.intValue()));
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
        // markdown转文本
        String text = MarkdownUtil.markdownToText(content);

        // 截取前200个字符
        if (text.length() > 200) {
            text = text.substring(0, 200);
        }
        return text;
    }
}
