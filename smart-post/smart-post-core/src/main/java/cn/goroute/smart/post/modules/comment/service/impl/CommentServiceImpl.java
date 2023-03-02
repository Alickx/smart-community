package cn.goroute.smart.post.modules.comment.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.StatusConstant;
import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.exception.BusinessException;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.common.util.PageUtil;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.domain.dto.CommentDTO;
import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.form.CommentForm;
import cn.goroute.smart.post.domain.qo.CommentQO;
import cn.goroute.smart.post.domain.vo.CommentVO;
import cn.goroute.smart.post.modules.article.mapper.PostMapper;
import cn.goroute.smart.post.modules.comment.converter.converter.CommentConverter;
import cn.goroute.smart.post.modules.comment.manager.CommentManagerService;
import cn.goroute.smart.post.modules.comment.mapper.CommentMapper;
import cn.goroute.smart.post.modules.comment.mq.CommentEventMessageTemplate;
import cn.goroute.smart.post.modules.comment.service.CommentService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentEntity>
        implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentManagerService commentManagerService;
    private final CommentEventMessageTemplate commentEventMessageTemplate;
    private final PostMapper postMapper;
	@Autowired
	private RedisUtil redisUtil;

	/**
     * 分页查询
     *
     * @param pageParam 分页参数
     * @param commentQO 查询参数
     * @return 分页结果
     */
    @Override
    public R<PageResult<CommentVO>> queryPage(PageParam pageParam, CommentQO commentQO) {

        IPage<CommentEntity> prodPage = PageUtil.prodPage(pageParam);

        IPage<CommentEntity> commentEntityIPage = commentMapper.queryPage(prodPage, commentQO, StatusConstant.NORMAL_STATUS, StatusConstant.NORMAL_STATUS);

        IPage<CommentVO> commentVOIPage = commentEntityIPage.convert(CommentConverter.INSTANCE::poToVO);

        List<CommentVO> records = commentVOIPage.getRecords();

        commentManagerService.fillInfo(records);

        return R.ok(new PageResult<>(records, commentVOIPage.getTotal()));

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
			redisUtil.hIncrBy(PostRedisConstant.PostKey.POST_COMMENT_COUNT_KEY, commentEntity.getPostId().toString(), -1L);
            return R.ok();
        }
        log.error("删除评论/回复失败，commentVO:[{}]", commentForm);
        throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public R<List<CommentVO>> queryMoreReply(CommentQO commentQO) {
        List<CommentEntity> commentEntities = commentMapper.queryMoreReply(commentQO, StatusConstant.NORMAL_STATUS,StatusConstant.NORMAL_STATUS);

        List<CommentVO> commentVOS = CommentConverter.INSTANCE.poToVO(commentEntities);

        commentManagerService.fillInfo(commentVOS);

        return R.ok(commentVOS);
    }

    /**
     * 通过评论查询文章id
     *
     * @param pageParam 分页参数
     * @param userId    用户id
     * @return 分页结果
     */
    @Override
    public PageResult<Long> queryPostIdsByComment(PageParam pageParam, Long userId) {

        IPage<Long> prodPage = PageUtil.prodPage(pageParam);

        IPage<CommentEntity> commentEntityIPage = baseMapper.queryPostIdsByComment(prodPage, userId, StatusConstant.NORMAL_STATUS, StatusConstant.NORMAL_STATUS);

        IPage<Long> pageResult = commentEntityIPage.convert(CommentEntity::getPostId);

        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal());
    }

	/**
	 * 通过文章id查询评论数
	 * @param postId 文章id
	 * @return 评论数
	 */
	@Override
	public Long queryCommentNumByPostId(Long postId) {

		// 从缓存中获取
		String value = (String) redisUtil
			.hGet(PostRedisConstant.PostKey.POST_COMMENT_COUNT_KEY, postId.toString());

		if (StrUtil.isNotBlank(value)) {
			return Long.valueOf(value);
		}

		// 缓存中没有，从数据库中获取
		// TODO 宽表查询

		return 0L;

	}

}




