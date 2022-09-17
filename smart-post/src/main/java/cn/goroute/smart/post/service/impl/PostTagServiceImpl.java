package cn.goroute.smart.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.post.domain.PostTag;
import cn.goroute.smart.post.service.PostTagService;
import cn.goroute.smart.post.mapper.PostTagMapper;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【post_tag(文章标签表)】的数据库操作Service实现
* @createDate 2022-09-17 19:31:22
*/
@Service
public class PostTagServiceImpl extends ServiceImpl<PostTagMapper, PostTag>
    implements PostTagService{

}




