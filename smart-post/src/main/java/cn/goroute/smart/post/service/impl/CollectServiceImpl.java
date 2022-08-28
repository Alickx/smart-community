package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.mapper.CollectMapper;
import cn.goroute.smart.common.entity.pojo.Collect;
import cn.goroute.smart.post.service.CollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("collectService")
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

}