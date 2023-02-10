package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.model.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.model.dto.PostInfoDTO;
import cn.goroute.smart.post.model.dto.PostViewRankDTO;
import cn.goroute.smart.post.model.qo.PostQO;
import cn.goroute.smart.post.model.vo.PostVO;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
	R<PageResult<PostAbbreviationDTO>> infoPage(PageParam pageParam, PostQO postQO);

	/**
	 * 根据文章Id查询文章详情
	 * @param postId 文章Id
	 * @return 文章详情
	 */
	R<PostInfoDTO> info(Long postId);

	/**
	 * 保存文章
	 * @param postVO 文章视图对象
	 * @return 文章Id
	 */
	R<Long> savePost(PostVO postVO, HttpServletRequest request);

	/**
	 * 查询评论过的文章
	 * @param pageParam 分页参数
	 * @param postQO 查询参数对象
	 * @return 查询结果
	 */
	R<PageResult<PostAbbreviationDTO>> queryByComment(PageParam pageParam, PostQO postQO);

	/**
	 * 文章风控处理
	 * @param post 文章对象
	 */
	void PostRiskHandler(Post post);

    R<List<PostViewRankDTO>> queryTodayViewRank();
}
