package cn.goroute.smart.post.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.post.domain.form.CommentForm;
import cn.goroute.smart.post.domain.qo.CommentQO;
import cn.goroute.smart.post.domain.vo.CommentVO;
import cn.goroute.smart.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/11/16/1:16
 * @Description: 文章评论
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	/**
	 * 分页查询
	 *
	 * @param pageParam 分页参数
	 * @param commentQO 查询参数
	 * @return 分页结果
	 */
	@GetMapping("/page")
	public R<PageResult<CommentVO>> page(@Validated PageParam pageParam, CommentQO commentQO) {
		return commentService.queryPage(pageParam, commentQO);
	}

	/**
	 * 保存评论
	 * @param commentForm 评论信息
	 * @return 保存结果
	 */
	@PostMapping("/save")
	@SaCheckLogin
	public R<Long> save(@RequestBody @Valid CommentForm commentForm) {
		return commentService.commentSave(commentForm);
	}

	/**
	 * 删除评论
	 * @param commentForm 评论信息
	 * @return 删除结果
	 */
	@PostMapping("/delete")
	@SaCheckLogin
	public R<Boolean> delete(@RequestBody @Valid CommentForm commentForm) {
		return commentService.commentDelete(commentForm);
	}

	@GetMapping("/queryMoreReply")
	public R<List<CommentVO>> queryMoreReply(CommentQO commentQO) {
		return commentService.queryMoreReply(commentQO);
	}


}
