package cn.goroute.smart.post.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.post.model.dto.CommentDTO;
import cn.goroute.smart.post.model.qo.CommentQO;
import cn.goroute.smart.post.model.vo.CommentVO;
import cn.goroute.smart.post.service.CommentService;
import com.hccake.ballcat.common.core.validation.group.UpdateGroup;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
	public R<PageResult<CommentDTO>> page(@Validated PageParam pageParam, CommentQO commentQO) {
		return commentService.queryPage(pageParam, commentQO);
	}

	/**
	 * 保存评论
	 * @param commentVO 评论信息
	 * @return 保存结果
	 */
	@PostMapping("/save")
	@SaCheckLogin
	public R<Long> save(@RequestBody @Validated(value = {UpdateGroup.class}) CommentVO commentVO) {
		return commentService.commentSave(commentVO);
	}

	/**
	 * 删除评论
	 * @param commentVO 评论信息
	 * @return 删除结果
	 */
	@PostMapping("/delete")
	@SaCheckLogin
	public R<Boolean> delete(@RequestBody @Validated(value = {UpdateGroup.class}) CommentVO commentVO) {
		return commentService.commentDelete(commentVO);
	}

	@GetMapping("/queryMoreReply")
	public R<List<CommentDTO>> queryMoreReply(CommentQO commentQO) {
		return commentService.queryMoreReply(commentQO);
	}


}
