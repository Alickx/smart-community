package cn.goroute.smart.post.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.goroute.smart.common.dao.PostTagDao;
import cn.goroute.smart.common.entity.pojo.PostTag;
import cn.goroute.smart.post.service.PostTagService;


/**
 * @author Alickx
 */
@Service("postTagService")
public class PostTagServiceImpl extends ServiceImpl<PostTagDao, PostTag> implements PostTagService {

}