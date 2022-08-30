package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.member.entity.pojo.Collect;
import cn.goroute.smart.member.mapper.CollectMapper;
import cn.goroute.smart.member.service.CollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("collectService")
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

}