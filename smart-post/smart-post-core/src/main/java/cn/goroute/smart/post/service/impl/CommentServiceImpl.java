package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import cn.goroute.smart.post.converter.CommentConverter;
import cn.goroute.smart.post.domain.dto.CommentDTO;
import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.form.CommentForm;
import cn.goroute.smart.post.domain.qo.CommentQO;
import cn.goroute.smart.post.domain.qo.PostQO;
import cn.goroute.smart.post.domain.vo.CommentVO;
import cn.goroute.smart.post.manager.CommentManagerService;
import cn.goroute.smart.post.mapper.CommentMapper;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.mq.CommentEventMessageTemplate;
import cn.goroute.smart.post.service.CommentService;
import com.hccake.ballcat.common.core.exception.BusinessException;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author Alickx
* @description 针对表【comment(文章回复表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl extends ExtendServiceImpl<CommentMapper, CommentEntity>
    implements CommentService{

	private final CommentMapper commentMapper;
	private final CommentManagerService commentManagerService;
	private final CommentEventMessageTemplate commentEventMessageTemplate;
	private final PostMapper postMapper;

	/**
	 * 分页查询
	 *
	 * @param pageParam 分页参数
	 * @param commentQO 查询参数
	 * @return 分页结果
	 */
	@Override
	public R<PageResult<CommentVO>> queryPage(PageParam pageParam, CommentQO commentQO) {

		PageResult<CommentVO> commentDTOPageResult = commentMapper.queryPage(pageParam, commentQO);

		List<CommentVO> records = commentDTOPageResult.getRecords();

		commentManagerService.fillInfo(records);

		return R.ok(commentDTOPageResult);

	}


	/**
	 * 保存评论/回复
	 *
	 * @param commentForm 评论/回复信息
	 * @return 保存结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Long> commentSave(CommentForm commentForm) {

		CommentEntity commentEntity = CommentConverter.INSTANCE.formToPo(commentForm);
		commentEntity.setUserId(StpUtil.getLoginIdAsLong());

		Long postId = commentEntity.getPostId();
		PostEntity postEntity = postMapper.selectById(postId);

		if (null == postEntity) {
			return R.failed(ErrorCodeEnum.PARAM_ERROR);
		}

		commentManagerService.saveCommentHandle(commentEntity);

		// 发送通知
		CommentDTO commentDTO = CommentConverter.INSTANCE.poToDto(commentEntity);
		commentDTO.setPostTitle(postEntity.getTitle());
		commentEventMessageTemplate.sendPostCommentMessage(commentDTO);

		return R.ok(commentEntity.getId());
	}

	/**
	 * 删除评论/回复
	 *
	 * @param commentForm 评论/回复信息
	 * @return 删除结果
	 */
	@Override
	public R<Boolean> commentDelete(CommentForm commentForm) {

		CommentEntity commentEntity = this.getById(commentForm.getId());
		if (commentEntity == null) {
			throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
		}
		boolean remove = this.removeById(commentForm.getId());
		if (remove) {
			return R.ok();
		}
		log.error("删除评论/回复失败，commentVO:[{}]", commentForm);
		throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
	}

	@Override
	public R<List<CommentVO>> queryMoreReply(CommentQO commentQO) {
		List<CommentVO> commentVOS = commentMapper.queryMoreReply(commentQO);
		commentManagerService.fillInfo(commentVOS);
		return R.ok(commentVOS);
	}

	/**
	 * 通过评论查询文章id
	 * @param pageParam 分页参数
	 * @param postQO 查询参数
	 * @return 分页结果
	 */
	@Override
	public PageResult<Long> queryPostIdsByComment(PageParam pageParam, PostQO postQO) {
		return baseMapper.queryPostIdsByComment(pageParam, postQO);
	}

}




