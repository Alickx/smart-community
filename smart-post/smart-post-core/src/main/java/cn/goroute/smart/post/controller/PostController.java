package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.post.domain.dto.PostViewRankDTO;
import cn.goroute.smart.post.domain.qo.PostQO;
import cn.goroute.smart.post.domain.vo.PostAbbreviationVO;
import cn.goroute.smart.post.domain.vo.PostInfoVO;
import cn.goroute.smart.post.domain.vo.PostVO;
import cn.goroute.smart.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
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
	public R<PageResult<PostAbbreviationVO>> infoPage(@Validated PageParam pageParam, PostQO postQO) {
		return postService.infoPage(pageParam, postQO);
	}

	/**
	 * 文章详情 - 根据id查询
	 *
	 * @param postId 文章id
	 * @return 查询结果
	 */
	@GetMapping("/info/{postId}")
	public R<PostInfoVO> info(@PathVariable("postId") Long postId) {
		return postService.info(postId);
	}

	/**
	 * 保存文章
	 * @param postVO 文章信息
	 * @return 文章保存主键
	 */
	@PostMapping("/save")
	public R<Long> save(@RequestBody @Valid PostVO postVO) {
		return postService.savePost(postVO);
	}


	@PostMapping("/delete/{postId}")
	public R<Boolean> delete(@PathVariable("postId") Long postId) {
		return postService.deletePost(postId);
	}

	/**
	 * 查询用户回复过的文章
	 * @param pageParam 分页参数
	 * @param postQO   文章查询对象
	 * @return 查询结果
	 */
	@GetMapping("/query/comment")
	public R<PageResult<PostAbbreviationVO>> queryByComment(@Validated PageParam pageParam, PostQO postQO) {
		return postService.queryByComment(pageParam, postQO);
	}

	@GetMapping("/query/todayViewRank")
	public R<List<PostViewRankDTO>> queryTodayViewRank() {
		return postService.queryTodayViewRank();
	}
}
