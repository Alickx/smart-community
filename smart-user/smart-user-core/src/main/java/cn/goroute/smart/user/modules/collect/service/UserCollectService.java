package cn.goroute.smart.user.modules.collect.service;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.post.domain.vo.PostAbbreviationVO;
import cn.goroute.smart.user.domain.entity.UserCollectEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author caiguopeng
* @description 针对表【user_collect(用户收藏表)】的数据库操作Service
* @createDate 2022-09-17 19:30:44
*/
public interface UserCollectService extends IService<UserCollectEntity> {

	/**
	 * 分页查询用户收藏
	 * @return 分页结果
	 */
	PageResult<PostAbbreviationVO> queryPage(PageParam pageParam);

	/**
	 * 保存用户收藏
	 * @param postId 文章id
	 * @return 是否成功 true:成功 false:失败
	 */
	Boolean saveUserCollect(Long postId);

	/**
	 * 删除用户收藏
	 * @param postId 文章id
	 * @return 是否成功 true:成功 false:失败
	 */
	Boolean deleteUserCollect(Long postId);

	/**
	 * 判断用户是否收藏
	 * @param postId 文章id
	 * @return 是否收藏 true:收藏 false:未收藏
	 */
	Boolean isExist(Long postId);
}
