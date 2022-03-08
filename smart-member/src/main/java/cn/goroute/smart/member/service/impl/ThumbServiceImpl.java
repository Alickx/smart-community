package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.common.dao.ThumbDao;
import cn.goroute.smart.common.entity.ThumbEntity;
import cn.goroute.smart.member.service.ThumbService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("thumbService")
public class ThumbServiceImpl extends ServiceImpl<ThumbDao, ThumbEntity> implements ThumbService {

}