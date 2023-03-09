package cn.goroute.smart.post.modules.article.service;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.post.domain.dto.PostViewRankDTO;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.qo.PostQO;
import cn.goroute.smart.post.domain.vo.PostAbbreviationVO;
import cn.goroute.smart.post.domain.vo.PostInfoVO;
import cn.goroute.smart.post.domain.vo.PostVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Alickx
* @description 针对表【post(文章表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface PostService extends IService<PostEntity> {

	/**
	 * 文章详情 - 分页查询
	 * @param pageParam 分页参数
	 * @param postQO 查询参数对象
	 * @return 查询结果
	 */
	R<PageResult<PostAbbreviationVO>> infoPage(PageParam pageParam, PostQO postQO);

	/**
	 * 根据文章Id查询文章详情
	 * @param postId 文章Id
	 * @return 文章详情
	 */
	R<PostInfoVO> info(Long postId);

	/**
	 * 保存文章
	 * @param postVO 文章视图对象
	 * @return 文章Id
	 */
	R<Long> savePost(PostVO postVO);

	/**
	 * 查询评论过的文章
	 * @param pageParam 分页参数
	 * @param postQO 查询参数对象
	 * @return 查询结果
	 */
	R<PageResult<PostAbbreviationVO>> queryByComment(PageParam pageParam, PostQO postQO);


    R<List<PostViewRankDTO>> queryTodayViewRank();

    R<Boolean> deletePost(Long postId);
}