package cn.goroute.smart.post.modules.article.service;

import cn.goroute.smart.post.domain.PostExpandInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Alickx
* @description 针对表【t_post_expand_info(文章拓展信息)】的数据库操作Service
* @createDate 2023-03-04 11:12:47
*/
public interface PostExpandInfoEntityService extends IService<PostExpandInfoEntity> {

	/**
	 * 查询文章拓展信息
	 * @param postIds 文章id集合
	 * @return 文章拓展信息
	 */
	List<PostExpandInfoEntity> batchPostExpandInfo(List<Long> postIds);

}
