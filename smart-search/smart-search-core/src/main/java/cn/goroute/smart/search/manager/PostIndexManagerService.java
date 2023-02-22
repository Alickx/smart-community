package cn.goroute.smart.search.manager;

import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.search.converter.PostIndexConverter;
import cn.goroute.smart.search.feign.FeignUserService;
import cn.goroute.smart.search.model.dto.PostIndexDTO;
import cn.goroute.smart.search.model.index.PostIndex;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/15:13
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostIndexManagerService {

    private final FeignUserService feignUserService;


    /**
     * 获取查询结果
     *
     * @param searchHitList 查询结果
     * @return List<PostIndex> 文章集合
     */
    public List<PostIndex> getPostIndexList(List<SearchHit<PostIndex>> searchHitList) {

        List<PostIndex> postIndexList = new ArrayList<>(searchHitList.size());

        for (SearchHit<PostIndex> postHit : searchHitList) {
            PostIndex postIndex = postHit.getContent();
            // 获取高亮数据
            Map<String, List<String>> fields = postHit.getHighlightFields();
            if (fields.size() > 0) {
                BeanMap beanMap = BeanMap.create(postIndex);
                for (String name : fields.keySet()) {
                    beanMap.put(name, fields.get(name).get(0));
                }
            }
            postIndexList.add(postIndex);
        }

        return postIndexList;

    }

    /**
     * 填充作者信息
     *
     * @param postIndexList 文章集合
     * @param searchPage    分页信息
     * @return 组装后的文章集合
     */
    public PageResult<PostIndexDTO> fillAuthorInfo(List<PostIndex> postIndexList, SearchPage<PostIndex> searchPage) {

		if (CollUtil.isEmpty(postIndexList)) {
			return new PageResult<>();
		}

        List<Long> authorIds = postIndexList.stream().map(PostIndex::getAuthorId).toList();

        R<List<UserProfileVO>> userProfileList = feignUserService.batchGetUserProfile(authorIds);

        if (userProfileList.getCode() != 200) {
            log.error("获取用户信息失败");
            throw new RuntimeException("获取用户信息失败");
        }

		// 如果调用服务获取到的用户信息为空，则直接不填充
        if (CollUtil.isNotEmpty(userProfileList.getData())) {

			// 用hashmap存储获取到的用户信息，方便后面填充
            Map<Long, UserProfileVO> userProfileMap = new HashMap<>();
            for (UserProfileVO userProfileVO : userProfileList.getData()) {
                userProfileMap.put(userProfileVO.getUserId(), userProfileVO);
            }

            List<PostIndexDTO> postIndexDTOList = new ArrayList<>();
            for (PostIndex postIndex : postIndexList) {
                PostIndexDTO postIndexDTO = PostIndexConverter.INSTANCE.indexToDTO(postIndex);
                postIndexDTO.setAuthor(userProfileMap.get(postIndex.getAuthorId()));
                postIndexDTOList.add(postIndexDTO);
            }

            PageResult<PostIndexDTO> pageResult = new PageResult<>();
            pageResult.setTotal(searchPage.getTotalElements());
            pageResult.setRecords(postIndexDTOList);

            return pageResult;
        }

		log.error("获取远程用户信息失败，请检查服务是否正常");

        PageResult<PostIndexDTO> pageResult = new PageResult<>();
        pageResult.setTotal(searchPage.getTotalElements());
		List<PostIndexDTO> postIndexDTOS = PostIndexConverter.INSTANCE.indexToDTO(postIndexList);
		pageResult.setRecords(postIndexDTOS);

        return pageResult;

    }
}
