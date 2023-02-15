package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.entity.ThumbEntity;
import cn.goroute.smart.post.domain.form.ThumbForm;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

/**
* @author Alickx
* @description 针对表【thumb(点赞表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface ThumbService extends ExtendService<ThumbEntity> {

	/**
	 * 保存点赞
	 * @param thumbForm 点赞请求
	 * @return 是否成功
	 */
	R<Boolean> saveThumb(ThumbForm thumbForm);

	/**
	 * 取消点赞
	 * @param thumbForm 点赞请求
	 * @return 是否成功
	 */
	R<Boolean> cancelThumb(ThumbForm thumbForm);
}
