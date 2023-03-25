package cn.goroute.smart.user.modules.profile.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.auth.domain.dto.AuthUserDTO;
import cn.goroute.smart.auth.domain.entity.AuthUserEntity;
import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.common.util.PageUtil;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.domain.dto.PostPublicEventDTO;
import cn.goroute.smart.user.constant.UserRedisConstant;
import cn.goroute.smart.user.domain.entity.UserFollowEntity;
import cn.goroute.smart.user.domain.entity.UserProfileEntity;
import cn.goroute.smart.user.domain.form.UserProfileQueryForm;
import cn.goroute.smart.user.domain.form.UserProfileUploadForm;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import cn.goroute.smart.user.modules.profile.converter.UserProfileConverter;
import cn.goroute.smart.user.modules.profile.manager.UserProfileManager;
import cn.goroute.smart.user.modules.profile.mapper.UserProfileMapper;
import cn.goroute.smart.user.modules.profile.service.UserProfileService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author caiguopeng
 * @description 针对表【user_profile(用户信息表)】的数据库操作Service实现
 * @createDate 2022-09-17 19:30:44
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfileEntity>
        implements UserProfileService {

    private final UserProfileMapper userProfileMapper;

    private final UserProfileManager userProfileManager;
	private final RedisUtil redisUtil;

	/**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @Override
    public R<UserProfileVO> getUserProfile(Long userId) {

        if (userId == null) {
            userId = StpUtil.getLoginIdAsLong();
        }

        UserProfileEntity userProfileEntity = userProfileManager.getUserProfile(userId);
        UserProfileVO userProfileVO = UserProfileConverter.INSTANCE
                .poToVo(userProfileEntity);

        return R.ok(userProfileVO);
    }

    /**
     * 初始化用户信息
     *
     * @param authUserDTO 用户信息
     * @return 是否成功
     */
    @Override
	@Transactional(rollbackFor = Exception.class)
    public R<Boolean> initUserProfile(AuthUserDTO authUserDTO) {

        UserProfileEntity userProfileEntity = UserProfileConverter.INSTANCE.authUserDTOToPo(authUserDTO);
        userProfileEntity.setAvatar("https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20220414145043.jpg");
		userProfileMapper.insert(userProfileEntity);

		return R.ok(true);
    }

    /**
     * 批量获取用户信息
     *
     * @param userIds 用户id集合
     * @return 用户信息集合
     */
    @Override
    public List<UserProfileVO> batchGetUserProfile(List<Long> userIds) {

        if (CollUtil.isEmpty(userIds)) {
            return ListUtil.empty();
        }

        List<UserProfileEntity> userProfileEntities = userProfileManager.batchGetUserProfile(userIds);

        return UserProfileConverter.INSTANCE.poToVo(userProfileEntities);

    }

    /**
     * 更新用户信息
     *
     * @param userProfileUploadForm 用户信息
     * @return 是否成功
     */
    @Override
    public R<Boolean> updateUserProfile(UserProfileUploadForm userProfileUploadForm) {

		long userId = StpUtil.getLoginIdAsLong();
		UserProfileEntity userProfileEntity = UserProfileConverter.INSTANCE.formToPo(userProfileUploadForm);
		userProfileEntity.setUserId(userId);

        int update = userProfileMapper.update(userProfileEntity,
                new LambdaUpdateWrapper<UserProfileEntity>().eq(UserProfileEntity::getUserId, userId));

		if (update > 0) {
			// 删除用户缓存
			String userProfileKey = UserRedisConstant.USER_PROFILE + ":" + userId;
			redisUtil.delete(userProfileKey);
		}

        return R.ok(update > 0);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void postPublicEventHandle(PostPublicEventDTO postPublicEventDTO) {

		// 更新用户发帖数
		userProfileMapper.updateArticleNum(postPublicEventDTO.getUserId(),1);

		// 删除用户缓存
		String userProfileKey = UserRedisConstant.USER_PROFILE + ":" + postPublicEventDTO.getUserId();
		redisUtil.delete(userProfileKey);

	}

    /**
     * 关注事件处理
     * @param entity 关注信息
     * @param isSave 是否保存关注 true:保存 false:取消关注
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void followHandler(UserFollowEntity entity, boolean isSave) {

        if (isSave) {
            // 增加用户粉丝数 DB
            userProfileMapper.updateFansNum(entity.getToUserId(),1);
            userProfileMapper.updateFollowNum(entity.getUserId(),1);
            // 删除db缓存
            String userIdKey = UserRedisConstant.USER_PROFILE + ":" + entity.getToUserId();
            String toUserKey = UserRedisConstant.USER_PROFILE + ":" + entity.getUserId();
            redisUtil.delete(Lists.newArrayList(userIdKey, toUserKey));
        } else {
            // 减少用户粉丝数 DB
            userProfileMapper.updateFollowNum(entity.getToUserId(),-1);
            // 减少用户关注数 DB
            userProfileMapper.updateFollowNum(entity.getUserId(),-1);
            // 删除db缓存
            String userProfileKey = UserRedisConstant.USER_PROFILE + ":" + entity.getToUserId();
            redisUtil.delete(userProfileKey);
        }

    }

	@Override
	public PageResult<UserProfileEntity> pageQuery(PageParam pageParam, UserProfileQueryForm form) {

		IPage<UserProfileEntity> prodPage = PageUtil.prodPage(pageParam);
		LambdaQueryWrapper<UserProfileEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserProfileEntity::getUserId,form.getUserId());
		wrapper.like(UserProfileEntity::getNickName,form.getNickName());
		IPage<UserProfileEntity> selectPage = this.baseMapper.selectPage(prodPage, wrapper);
		return PageUtil.prodPageResult(selectPage);
	}

}




