package cn.goroute.smart.post.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.goroute.smart.common.dao.TagDao;
import cn.goroute.smart.common.entity.pojo.Tag;
import cn.goroute.smart.post.service.TagService;


@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {


}