package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.Thumb;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

/**
* @author Alickx
* @description 针对表【thumb(点赞表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface ThumbService extends ExtendService<Thumb> {

	/**
	 * 保存点赞
	 * @param postId 文章Id
	 * @return 是否成功
	 */
	R<Boolean> saveThumb(Long postId);
}
