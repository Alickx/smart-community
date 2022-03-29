package cn.goroute.smart.post.service;

import cn.goroute.smart.common.entity.pojo.Thumb;
import cn.goroute.smart.common.entity.vo.PostQueryListVO;
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

    int trans();

    Result listByMemberUid(PostQueryListVO postQueryListVO);

    int transCount();

}
