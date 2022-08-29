package cn.goroute.smart.post.service;

import cn.goroute.smart.post.entity.pojo.Thumb;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Alickx
* @description 针对表【t_thumb(点赞表)】的数据库操作Service
* @createDate 2022-03-23 16:44:34
*/
public interface ThumbService extends IService<Thumb> {

    Result thumbSave(Thumb thumb);

    Result thumbCancel(Thumb thumb);

    /**
     * 持久化点赞数据
     * @return 点赞数据条数
     */
    int trans();

    Result listByMemberUid(QueryParam queryParam);

    /**
     * 持久化点赞评论数量
     * @return 持久化数量
     */
    int transCount();

}
