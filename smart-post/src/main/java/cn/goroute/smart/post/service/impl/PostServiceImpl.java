package cn.goroute.smart.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.service.PostService;
import cn.goroute.smart.post.mapper.PostMapper;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【post(文章表)】的数据库操作Service实现
* @createDate 2022-09-17 19:31:22
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService{

}




