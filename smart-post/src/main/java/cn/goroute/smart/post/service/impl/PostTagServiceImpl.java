package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.PostTag;
import cn.goroute.smart.post.mapper.PostTagMapper;
import cn.goroute.smart.post.service.PostTagService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【post_tag(文章标签表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
public class PostTagServiceImpl extends ExtendServiceImpl<PostTagMapper, PostTag>
    implements PostTagService{

}




