package cn.goroute.smart.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.post.domain.Tag;
import cn.goroute.smart.post.service.TagService;
import cn.goroute.smart.post.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【tag(标签表)】的数据库操作Service实现
* @createDate 2022-09-17 19:31:22
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




