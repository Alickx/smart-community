package cn.goroute.smart.post.mapper;

import cn.goroute.smart.post.domain.Post;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;

/**
* @author Alickx
* @description 针对表【post(文章表)】的数据库操作Mapper
* @createDate 2022-09-25 16:53:24
* @Entity cn.goroute.smart.post.domain.Post
*/
public interface PostMapper extends ExtendMapper<Post> {

    default PageResult<Post> queryPage(PageParam pageParam) {
        IPage<Post> page = this.prodPage(pageParam);
        this.selectPage(page, null);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

}




