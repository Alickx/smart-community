package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.annoation.LogTime;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.model.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.model.dto.PostInfoDTO;
import cn.goroute.smart.post.model.dto.PostViewRankDTO;
import cn.goroute.smart.post.model.qo.PostQO;
import cn.goroute.smart.post.model.vo.PostVO;
import cn.goroute.smart.post.service.PostService;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/09/26/0:05
 * @Description: 技术文章接口
 */
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	/**
	 * 文章详情 - 分页查询
	 *
	 * @param pageParam 分页参数
	 * @param postQO    文章查询对象
	 * @return 查询结果
	 */
	@GetMapping("/info/page")
	@LogTime
	public R<PageResult<PostAbbreviationDTO>> infoPage(@Validated PageParam pageParam, PostQO postQO) {
		return postService.infoPage(pageParam, postQO);
	}

	/**
	 * 文章详情 - 根据id查询
	 *
	 * @param postId 文章id
	 * @return 查询结果
	 */
	@GetMapping("/info/{postId}")
	public R<PostInfoDTO> info(@PathVariable("postId") Long postId) {
		return postService.info(postId);
	}

	/**
	 * 保存文章
	 * @param postVO 文章信息
	 * @return 文章保存主键
	 */
	@PostMapping("/save")
	public R<Long> save(@RequestBody @Valid PostVO postVO, HttpServletRequest request) {
		return postService.savePost(postVO,request);
	}

	/**
	 * 查询用户回复过的文章
	 * @param pageParam 分页参数
	 * @param postQO   文章查询对象
	 * @return 查询结果
	 */
	@GetMapping("/query/comment")
	public R<PageResult<PostAbbreviationDTO>> queryByComment(@Validated PageParam pageParam, PostQO postQO) {
		return postService.queryByComment(pageParam, postQO);
	}

	@GetMapping("/query/todayViewRank")
	public R<List<PostViewRankDTO>> queryTodayViewRank() {
		return postService.queryTodayViewRank();
	}


	/**
	 * 内部服务间调用 - 根据id查询文章
	 * @param postId 文章id
	 * @return 文章实体类
	 */
	@GetMapping("/get/{postId}")
	@Operation(summary = "根据文章id获取文章实体类", description = "内部服务间调用")
	public R<Post> getById(@PathVariable("postId") Long postId) {
		return R.ok(postService.getById(postId));
	}
}
