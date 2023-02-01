package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.Tag;
import cn.goroute.smart.post.mapper.TagMapper;
import cn.goroute.smart.post.service.TagService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【tag(标签表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ExtendServiceImpl<TagMapper, Tag>
    implements TagService{

}




