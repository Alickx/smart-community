package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.common.dao.SectionDao;
import cn.goroute.smart.common.entity.pojo.Section;
import cn.goroute.smart.post.service.SectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("sectionService")
public class SectionServiceImpl extends ServiceImpl<SectionDao, Section> implements SectionService {


}