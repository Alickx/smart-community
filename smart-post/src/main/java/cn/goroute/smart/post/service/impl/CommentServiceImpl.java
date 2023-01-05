package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.ErrorCodeEnum;
import cn.goroute.smart.post.converter.CommentConverter;
import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.manage.CommentManageService;
import cn.goroute.smart.post.mapper.CommentMapper;
import cn.goroute.smart.post.model.dto.CommentDTO;
import cn.goroute.smart.post.model.qo.CommentQO;
import cn.goroute.smart.post.model.vo.CommentVO;
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
public class CommentServiceImpl extends ExtendServiceImpl<CommentMapper, Comment>
    implements CommentService{

	private final CommentMapper commentMapper;

	private final CommentManageService commentManageService;

	/**
	 * 分页查询
	 *
	 * @param pageParam 分页参数
	 * @param commentQO 查询参数
	 * @return 分页结果
	 */
	@Override
	public R<PageResult<CommentDTO>> queryPage(PageParam pageParam, CommentQO commentQO) {

		PageResult<CommentDTO> commentDTOPageResult = commentMapper.queryPage(pageParam, commentQO);

		List<CommentDTO> records = commentDTOPageResult.getRecords();

		commentManageService.fillInfo(records);

		return R.ok(commentDTOPageResult);

	}


	/**
	 * 保存评论/回复
	 *
	 * @param commentVO 评论/回复信息
	 * @return 保存结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> commentSave(CommentVO commentVO) {

		Comment comment = CommentConverter.INSTANCE.voToPo(commentVO);
		comment.setUserId(StpUtil.getLoginIdAsLong());
		boolean save = this.save(comment);
		if (save) {
			//TODO 发布评论回复事件
			return R.ok(true);
		}
		log.error("保存评论/回复失败，commentVO:[{}]", commentVO);
		throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 删除评论/回复
	 *
	 * @param commentVO 评论/回复信息
	 * @return 删除结果
	 */
	@Override
	public R<Boolean> commentDelete(CommentVO commentVO) {

		Comment comment = this.getById(commentVO.getId());
		if (comment == null) {
			throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
		}
		boolean remove = this.removeById(commentVO.getId());
		if (remove) {
			return R.ok();
		}
		log.error("删除评论/回复失败，commentVO:[{}]", commentVO);
		throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
	}

}




