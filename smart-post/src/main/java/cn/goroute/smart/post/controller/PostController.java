package cn.goroute.smart.post.controller;

import cn.goroute.smart.post.entity.dto.PostDTO;
import cn.goroute.smart.post.entity.qo.PostQO;
import cn.goroute.smart.post.entity.vo.PostVO;
import cn.goroute.smart.post.service.PostService;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
	public R<PageResult<PostDTO>> infoPage(@Validated PageParam pageParam, PostQO postQO) {
		return postService.infoPage(pageParam, postQO);
	}

	/**
	 * 文章详情 - 根据id查询
	 *
	 * @param postId 文章id
	 * @return 查询结果
	 */
	@GetMapping("/info/{postId}")
	public R<PostDTO> info(@PathVariable("postId") Long postId) {
		return postService.info(postId);
	}

	/**
	 * 保存文章
	 * @param postVO 文章信息
	 * @return 文章保存主键
	 */
	@PostMapping("/save")
	public R<Long> save(@RequestBody PostVO postVO) {
		return postService.save(postVO);
	}


}
