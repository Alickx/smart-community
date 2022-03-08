package cn.goroute.smart.post.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;

import cn.goroute.smart.common.dao.PostTagDao;
import cn.goroute.smart.common.entity.PostTagEntity;
import cn.goroute.smart.post.service.PostTagService;


@Service("postTagService")
public class PostTagServiceImpl extends ServiceImpl<PostTagDao, PostTagEntity> implements PostTagService {

}