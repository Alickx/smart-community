package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.entity.dto.PostDTO;
import cn.goroute.smart.post.entity.qo.PostQO;
import cn.goroute.smart.post.entity.vo.PostVO;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【post(文章表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface PostService extends ExtendService<Post> {

	/**
	 * 文章详情 - 分页查询
	 * @param pageParam 分页参数
	 * @param postQO 查询参数对象
	 * @return 查询结果
	 */
	R<PageResult<PostDTO>> infoPage(PageParam pageParam, PostQO postQO);

	/**
	 * 根据文章Id查询文章详情
	 * @param postId 文章Id
	 * @return 文章详情
	 */
	R<PostDTO> info(Long postId);

	/**
	 * 保存文章
	 * @param postVO 文章视图对象
	 * @return 文章Id
	 */
	R<Long> save(PostVO postVO);
}
