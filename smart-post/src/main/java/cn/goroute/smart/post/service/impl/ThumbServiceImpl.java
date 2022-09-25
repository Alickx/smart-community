package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.post.service.ThumbService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【thumb(点赞表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
public class ThumbServiceImpl extends ExtendServiceImpl<ThumbMapper, Thumb>
    implements ThumbService{

}




