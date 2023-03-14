package cn.goroute.smart.post.modules.article.service;

import cn.goroute.smart.post.domain.ExpandInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Alickx
 * @description 针对表【expand_info(文章拓展信息)】的数据库操作Service
 * @createDate 2023-03-04 11:12:47
 */
public interface ExpandInfoService extends IService<ExpandInfoEntity> {

    /**
     * 查询文章拓展信息
     *
     * @param targetIds 文章id集合
     * @param type      类型
     * @return 文章拓展信息
     */
    List<ExpandInfoEntity> batchExpandInfo(List<Long> targetIds, Integer type);
}
