package cn.goroute.smart.post.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.goroute.smart.post.mapper.PostTagMapper;
import cn.goroute.smart.post.entity.pojo.PostTag;
import cn.goroute.smart.post.service.PostTagService;


/**
 * @author Alickx
 */
@Service("postTagService")
public class PostTagServiceImpl extends ServiceImpl<PostTagMapper, PostTag> implements PostTagService {

}