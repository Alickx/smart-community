package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.post.service.PostService;
import cn.goroute.smart.post.service.ThumbService;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【thumb(点赞表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class ThumbServiceImpl extends ExtendServiceImpl<ThumbMapper, Thumb>
    implements ThumbService{

	private final PostService postService;

	/**
	 * 保存点赞
	 *
	 * @param postId 文章Id
	 * @return 是否成功
	 */
	@Override
	public R<Boolean> saveThumb(Long postId) {

		//TODO 设计缓存数据结构
		return R.ok(true);

	}
}




