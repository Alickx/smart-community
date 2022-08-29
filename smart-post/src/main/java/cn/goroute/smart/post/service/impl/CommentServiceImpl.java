package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.common.api.ResultCode;
import cn.goroute.smart.common.constant.PostConstant;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.entity.bo.EventRemindBo;
import cn.goroute.smart.common.entity.dto.MemberDto;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.feign.MemberFeignService;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.*;
import cn.goroute.smart.post.entity.dto.CommentDto;
import cn.goroute.smart.post.entity.pojo.Comment;
import cn.goroute.smart.post.entity.pojo.Post;
import cn.goroute.smart.post.entity.pojo.Thumb;
import cn.goroute.smart.post.entity.vo.CommentVo;
import cn.goroute.smart.post.mapper.CommentMapper;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.post.util.ConvertRemindUtil;
import cn.goroute.smart.post.util.RabbitmqUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alickx
 * @description 针对表【t_comment(文章回复表)】的数据库操作Service实现
 * @createDate 2022-03-05 08:39:52
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    PostMapper postMapper;

    @Resource
    ThumbMapper thumbMapper;

    @Autowired
    RabbitmqUtil rabbitmqUtil;

    @Autowired
    AuthService authService;

    /**
     * 根据文章id获取文章评论列表
     *
     * @param queryParam 查询参数
     * @param postUid    文章id
     * @return 评论列表
     * @throws IOException io异常
     */
    @Override
    public Result getCommentByPost(QueryParam queryParam, Long postUid) throws IOException {

        IPage<Comment> pageResult = commentMapper.selectPage(new Query<Comment>().getPage(queryParam),
                new LambdaQueryWrapper<Comment>().eq(Comment::getPostId, postUid)
                        .isNull(Comment::getFirstCommentId)
                        .eq(Comment::getStatus, PostConstant.NORMAL_STATUS));

        if (pageResult.getRecords().isEmpty()) {
            return Result.ok().put("data", new PageUtils(pageResult));
        }

        List<Comment> commentList = pageResult.getRecords();

        List<CommentDto> commentDTOList = new ArrayList<>();

        CommentDto commentDTO;

        for (Comment comment : commentList) {
            commentDTO = new CommentDto();

            MemberDto fromMember = memberFeignService.getMemberByUid(comment.getMemberId());
            MemberDto toMember = memberFeignService.getMemberByUid(comment.getToMemberId());

            Long thumbCount = getThumbCount(comment);
            boolean isLike = isLike(comment);

            PageUtils reply = getReply(comment.getId());
            commentDTO.setId(comment.getId());
            commentDTO.setFromMember(fromMember);
            commentDTO.setToMember(toMember);
            commentDTO.setContent(comment.getContent());
            commentDTO.setCreatedTime(comment.getCreatedTime());
            commentDTO.setReplyInfo((List<CommentDto>) reply.getList());
            commentDTO.setThumbCount(thumbCount);
            commentDTO.setIsLike(isLike);
            commentDTO.setHasMore(reply.getTotalPage() > 1);
            commentDTOList.add(commentDTO);
        }
        IPage<CommentDto> commentDTOPage = new Page<>();
        BeanUtils.copyProperties(pageResult, commentDTOPage);
        commentDTOPage.setRecords(commentDTOList);


        PageUtils page = new PageUtils(commentDTOPage);
        return Result.ok().put("data", page);
    }

    /**
     * 删除评论
     *
     * @param commentVo 评论VO
     * @return 删除结果
     */
    @Override
    public Result del(CommentVo commentVo) {

        Comment comment = commentMapper.selectById(commentVo.getId());
        if (comment != null) {
            long memberUid = comment.getMemberId();
            // 判断是否是评论者
            if (!Objects.equals(memberUid, authService.getLoginUid())) {
                return Result.error();
            }
            // 逻辑删除
            comment.setStatus(PostConstant.DELETE_STATUS);
            int result = commentMapper.updateById(comment);
            if (result != 1) {
                return Result.error();
            }
            //更新redis中的数据
            String key = RedisKeyConstant.POST_COUNT_KEY + comment.getPostId();
            if (redisUtil.hHasKey(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
                redisUtil.hdecr(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, 1);
            } else {
                synchronized (this) {
                    if (redisUtil.hHasKey(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
                        redisUtil.hdecr(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, 1);
                    } else {
                        Post post = postMapper.selectById(comment.getPostId());
                        if (post != null) {
                            redisUtil.hset(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, post.getCommentCount());
                            redisUtil.hdecr(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, 1);
                        }
                    }
                }
            }
            return Result.ok();
        }
        return Result.error(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
    }

    /**
     * 保存评论
     *
     * @param commentVo 评论VO
     * @return 保存结果
     */
    @Override
    public Result saveComment(CommentVo commentVo) {
        //审核评论
//        boolean checkResult = illegalTextCheckUtil.checkText(commentVo.getContent());
//        if (checkResult) {
//            return Result.error("请不要发送含有违禁词的评论");
//        }

        //判断点赞文章是否存在
        Post post = postMapper.selectOne(new LambdaQueryWrapper<Post>()
                .eq(Post::getId, commentVo.getPostId())
                .eq(Post::getStatus, PostConstant.NORMAL_STATUS)
                .eq(Post::getIsPublish, PostConstant.PUBLISH));

        if (post == null) {
            return Result.error(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVo, comment);

        comment.setMemberId(authService.getLoginUid());
        int result = commentMapper.insert(comment);
        if (result != 1) {
            throw new ServiceException("评论发布失败");
        }
        //发送消息通知
        EventRemindBo eventRemind = ConvertRemindUtil.convertCommentNotification(comment, post.getTitle());
        rabbitmqUtil.sendEventRemind(eventRemind);

        String key = RedisKeyConstant.POST_COUNT_KEY + commentVo.getPostId();
        //如果redis存在key，则直接增加
        if (redisUtil.hHasKey(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
            redisUtil.hincr(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, 1);
        } else {
            synchronized (this) {
                if (redisUtil.hHasKey(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
                    redisUtil.hincr(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, 1);
                } else {
                    redisUtil.hset(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, post.getCommentCount());
                    redisUtil.hincr(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, 1);
                }
            }
        }

        return Result.ok().put("data", comment.getId());
    }

    /**
     * 查询评论点赞数量
     *
     * @param comment 评论
     * @return 点赞数量
     * @throws IOException IO异常
     */
    private Long getThumbCount(Comment comment) throws IOException {
        Long thumbCount = thumbMapper.selectCount(new LambdaQueryWrapper<Thumb>()
                .eq(Thumb::getToId, comment.getId())
                .eq(Thumb::getType, PostConstant.THUMB_COMMENT_TYPE));


        String thumbScanKey = "*:" + comment.getId();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash()
                .scan(RedisKeyConstant.POST_THUMB_KEY, ScanOptions.scanOptions().match(thumbScanKey).build());

        while (cursor.hasNext()) {
            thumbCount++;
            cursor.next();
        }

        cursor.close();

        return thumbCount;
    }

    /**
     * 查询评论点赞状态
     *
     * @param comment 评论
     * @return 点赞状态
     */
    private boolean isLike(Comment comment) {
        boolean isLike = false;

        if (Boolean.TRUE.equals(authService.getIsLogin())) {
            long loginUid = authService.getLoginUid();

            String redisKey = RedisKeyConstant.getThumbKey(loginUid, comment.getId());

            if (!redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, redisKey)) {
                //数据库点赞记录
                Thumb thumb = thumbMapper.selectOne(new LambdaQueryWrapper<Thumb>().eq(Thumb::getMemberId, loginUid)
                        .eq(Thumb::getToId, comment.getId()));
                if (thumb != null) {
                    isLike = true;
                }
            } else {
                isLike = true;
            }
        }
        return isLike;
    }

    /**
     * 查询子评论列表
     *
     * @param firstCommentUid 父评论UID
     * @return 子评论列表
     * @throws IOException IO异常
     */
    private PageUtils getReply(Long firstCommentUid) throws IOException {

        QueryParam queryParam = new QueryParam();
        queryParam.setLimit("3");
        queryParam.setSidx("created_time");
        IPage<Comment> pageResult = commentMapper.selectPage(new Query<Comment>().getPage(queryParam), new QueryWrapper<Comment>()
                .eq("first_comment_uid", firstCommentUid)
                .eq("status", PostConstant.NORMAL_STATUS));

        List<Comment> commentList = pageResult.getRecords();
        List<CommentDto> commentDTOList = new ArrayList<>();

        CommentDto commentDTO;

        for (Comment comment : commentList) {
            commentDTO = new CommentDto();
            MemberDto fromMember = memberFeignService.getMemberByUid(comment.getMemberId());
            MemberDto toMember = memberFeignService.getMemberByUid(comment.getToMemberId());
            long thumbCount = getThumbCount(comment);
            boolean isLike = isLike(comment);
            commentDTO.setId(comment.getId());
            commentDTO.setFromMember(fromMember);
            commentDTO.setToMember(toMember);
            commentDTO.setContent(comment.getContent());
            commentDTO.setCreatedTime(comment.getCreatedTime());
            commentDTO.setThumbCount(thumbCount);
            commentDTO.setIsLike(isLike);
            commentDTO.setHasMore(false);
            commentDTOList.add(commentDTO);
        }
        IPage<CommentDto> commentDTOIPage = new Page<>();
        BeanUtils.copyProperties(pageResult, commentDTOIPage);
        commentDTOIPage.setRecords(commentDTOList);


        return new PageUtils(commentDTOIPage);

    }
}




