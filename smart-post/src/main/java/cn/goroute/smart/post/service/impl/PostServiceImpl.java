package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.common.constant.CommonConstant;
import cn.goroute.smart.post.converter.PostConverter;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.entity.dto.PostDTO;
import cn.goroute.smart.post.entity.qo.PostQO;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.service.PostService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【post(文章表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
@RequiredArgsConstructor
public class PostServiceImpl extends ExtendServiceImpl<PostMapper, Post>
    implements PostService{


	/**
	 * 文章详情 - 分页查询
	 * @param pageParam 分页参数
	 * @param postQO 查询参数对象
	 * @return 查询结果
	 */
	@Override
	public PageResult<PostDTO> infoPage(PageParam pageParam, PostQO postQO) {
		return baseMapper.queryPage(pageParam, postQO);
	}

	/**
	 * 根据文章Id查询文章详情
	 *
	 * @param postId 文章Id
	 * @return 文章详情
	 */
	@Override
	public PostDTO info(Long postId) {
		LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Post::getId, postId);
		wrapper.notIn(Post::getDeleted, CommonConstant.DELETE_STATE);
		wrapper.eq(Post::getState, CommonConstant.NORMAL_STATE);
		Post post = baseMapper.selectOne(wrapper);
		return PostConverter.INSTANCE.poToDto(post);
	}
}




