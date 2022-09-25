package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.service.PostService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【post(文章表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
public class PostServiceImpl extends ExtendServiceImpl<PostMapper, Post>
    implements PostService{

}




