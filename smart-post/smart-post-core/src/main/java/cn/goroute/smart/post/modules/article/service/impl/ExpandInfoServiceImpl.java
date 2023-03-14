package cn.goroute.smart.post.modules.article.service.impl;

import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import cn.goroute.smart.common.exception.BusinessException;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.constant.enums.PostItemTypeEnum;
import cn.goroute.smart.post.domain.ExpandInfoEntity;
import cn.goroute.smart.post.modules.article.converter.PostExpandInfoConverter;
import cn.goroute.smart.post.modules.article.mapper.PostExpandInfoEntityMapper;
import cn.goroute.smart.post.modules.article.service.ExpandInfoService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Alickx
 * @description 针对表【t_post_expand_info(文章拓展信息)】的数据库操作Service实现
 * @createDate 2023-03-04 11:12:47
 */
@RequiredArgsConstructor
@Service
public class ExpandInfoServiceImpl extends ServiceImpl<PostExpandInfoEntityMapper, ExpandInfoEntity>
        implements ExpandInfoService {

    private final RedisUtil redisUtil;

    /**
     * 查询文章拓展信息
     *
     * @param targetIds 文章id
     * @param type      类型
     * @return 文章拓展信息
     */
    @Override
    public List<ExpandInfoEntity> batchExpandInfo(List<Long> targetIds,Integer type) {

        String itemName = PostItemTypeEnum.getNameByCode(type);
        if (StrUtil.isBlank(itemName)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }

        List<ExpandInfoEntity> result = Lists.newArrayList();
        // 缓存中没有的，批量到数据库查询
        List<Long> dbQueryPostIds = Lists.newArrayList();

        for (Long targetId : targetIds) {

            String redisKey = PostRedisConstant.PostKey.EXPAND_INFO_KEY + itemName + ":" + targetId;
            Map<String, String> postExpandInfoMap = redisUtil.hGetAll(redisKey);

            if (postExpandInfoMap.isEmpty()) {
                dbQueryPostIds.add(targetId);
                continue;
            }

            ExpandInfoEntity expandInfoEntity = PostExpandInfoConverter.INSTANCE.mapToVo(postExpandInfoMap);
            result.add(expandInfoEntity);
        }

        // 如果部分数据在缓存中没有，则批量查询数据库
        if (CollUtil.isNotEmpty(dbQueryPostIds)) {
            LambdaQueryWrapper<ExpandInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(ExpandInfoEntity::getTargetId, dbQueryPostIds);
            queryWrapper.eq(ExpandInfoEntity::getType, type);

            List<ExpandInfoEntity> postExpandInfoEntities = this.list(queryWrapper);

            // 如果数据库中有数据，则放入缓存
            if (CollUtil.isNotEmpty(postExpandInfoEntities)) {
                for (ExpandInfoEntity expandInfoEntity : postExpandInfoEntities) {
                    String redisKey = PostRedisConstant.PostKey.EXPAND_INFO_KEY + itemName + ":" + expandInfoEntity.getTargetId();
                    // 将对象转换成map
                    BeanUtilsHashMapper<ExpandInfoEntity> map = new BeanUtilsHashMapper<>(ExpandInfoEntity.class);
                    redisUtil.hPutAll(redisKey, map.toHash(expandInfoEntity));
                    redisUtil.expire(redisKey, 6, TimeUnit.HOURS);
                    result.add(expandInfoEntity);
                }
            }
        }

        return result;
    }




}




