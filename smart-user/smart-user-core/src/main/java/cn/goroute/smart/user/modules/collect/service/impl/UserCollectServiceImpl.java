package cn.goroute.smart.user.modules.collect.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.exception.BusinessException;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.common.modules.result.SystemResultCode;
import cn.goroute.smart.common.util.PageUtil;
import cn.goroute.smart.post.domain.vo.PostAbbreviationVO;
import cn.goroute.smart.user.domain.entity.UserCollectEntity;
import cn.goroute.smart.user.feign.FeignPostService;
import cn.goroute.smart.user.modules.collect.mapper.UserCollectMapper;
import cn.goroute.smart.user.modules.collect.service.UserCollectService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author caiguopeng
 * @description 针对表【user_collect(用户收藏表)】的数据库操作Service实现
 * @createDate 2022-09-17 19:30:44
 */
@Service
@RequiredArgsConstructor
public class UserCollectServiceImpl extends ServiceImpl<UserCollectMapper, UserCollectEntity>
	implements UserCollectService {

	private final UserCollectMapper userCollectMapper;

	private final FeignPostService feignPostService;

	/**
	 * 分页查询用户收藏
	 *
	 * @param pageParam 分页参数
	 * @return 分页结果
	 */
	@Override
	public PageResult<PostAbbreviationVO> queryPage(PageParam pageParam) {

		IPage<UserCollectEntity> prodPage = PageUtil.prodPage(pageParam);
		IPage<UserCollectEntity> userCollectEntityIPage = userCollectMapper.queryPage(prodPage);

		List<UserCollectEntity> records = userCollectEntityIPage.getRecords();
		if (CollUtil.isEmpty(records)) {
			return new PageResult<>(0);
		}

		// 远程调用获取文章简略信息
		List<Long> postIds = records.stream().map(UserCollectEntity::getPostId).toList();

		R<List<PostAbbreviationVO>> result = feignPostService.batchInfo(postIds);

		if (result.getCode() != SystemResultCode.SUCCESS.getCode()) {
			throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
		}

		// 返回结果
		if (CollUtil.isNotEmpty(result.getData())) {
			return new PageResult<>(result.getData(),userCollectEntityIPage.getTotal());
		} else {
			return new PageResult<>(0);
		}

	}

	/**
	 * 保存用户收藏
	 *
	 * @param postId 文章id
	 * @return 是否成功 true:成功 false:失败
	 */
	@Override
	public Boolean saveUserCollect(Long postId) {

		long userId = StpUtil.getLoginIdAsLong();

		// 获取文章信息，判断文章是否存在
		R<Boolean> result = feignPostService.queryIsExist(postId);
		if (result.getCode() != SystemResultCode.SUCCESS.getCode()) {
			throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
		}

		// 文章存在
		if (result.getData()) {

			// 判断是否已经收藏
			LambdaQueryWrapper<UserCollectEntity> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(UserCollectEntity::getUserId, userId);
			queryWrapper.eq(UserCollectEntity::getPostId, postId);
			UserCollectEntity userCollectEntity = userCollectMapper.selectOne(queryWrapper);

			// 已经收藏
			if (userCollectEntity != null) {
				return true;
			}

			// 未收藏
			UserCollectEntity collectEntity = new UserCollectEntity();
			collectEntity.setUserId(userId);
			collectEntity.setPostId(postId);
			return userCollectMapper.insert(collectEntity) > 0;
		} else {
			return false;
		}

	}

	/**
	 * 删除用户收藏
	 *
	 * @param postId 文章id
	 * @return 是否成功 true:成功 false:失败
	 */
	@Override
	public Boolean deleteUserCollect(Long postId) {

		// 判断是否已经收藏
		long userId = StpUtil.getLoginIdAsLong();

		LambdaQueryWrapper<UserCollectEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(UserCollectEntity::getUserId, userId);
		queryWrapper.eq(UserCollectEntity::getPostId, postId);
		UserCollectEntity userCollectEntity = userCollectMapper.selectOne(queryWrapper);

		if (userCollectEntity == null) {
			return true;
		}

		return userCollectMapper.deleteById(userCollectEntity.getId()) > 0;

	}

	/**
	 * 判断用户是否收藏
	 *
	 * @param postId 文章id
	 * @return 是否收藏 true:收藏 false:未收藏
	 */
	@Override
	public Boolean isExist(Long postId) {

		long userId = StpUtil.getLoginIdAsLong();

		LambdaQueryWrapper<UserCollectEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(UserCollectEntity::getUserId, userId);
		queryWrapper.eq(UserCollectEntity::getPostId, postId);
		UserCollectEntity userCollectEntity = userCollectMapper.selectOne(queryWrapper);

		return userCollectEntity != null;

	}
}




