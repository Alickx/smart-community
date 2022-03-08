package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.dao.CommentDao;
import cn.goroute.smart.common.entity.Comment;
import cn.goroute.smart.common.entity.CommentDTO;
import cn.goroute.smart.common.entity.MemberDTO;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.post.feign.MemberFeignService;
import cn.goroute.smart.post.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

import static cn.goroute.smart.common.utils.Constant.CloudService.getThumbOrCollectKey;
import static cn.goroute.smart.common.utils.Constant.DEFAULT_STATUS;
import static cn.goroute.smart.common.utils.Constant.POST_THUMB_KEY;

/**
 * @author Alickx
 * @description 针对表【t_comment(文章回复表)】的数据库操作Service实现
 * @createDate 2022-03-05 08:39:52
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment>
        implements CommentService {


    @Resource
    CommentDao commentDao;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public R getCommentByPost(String postUid) throws IOException {

        List<Comment> commentList = commentDao.selectList(new QueryWrapper<Comment>()
                .eq("post_uid", postUid)
                .eq("status", DEFAULT_STATUS));


        List<CommentDTO> commentDTOList = new ArrayList<>();

        CommentDTO commentDTO;

        for (Comment comment : commentList) {
            commentDTO = new CommentDTO();

            MemberDTO fromMember = memberFeignService.getMemberByUid(comment.getMemberUid());
            MemberDTO toMember = memberFeignService.getMemberByUid(comment.getToMemberUid());

            int thumbCount = getThumbCount(comment);

            boolean isLike = isLike(comment);
            commentDTO.setUid(comment.getUid());
            commentDTO.setFromMember(fromMember);
            commentDTO.setToMember(toMember);
            commentDTO.setContent(comment.getContent());
            commentDTO.setCreatedTime(comment.getCreatedTime());
            commentDTO.setReplyInfo(getSonCommentPage(comment.getUid()));
            commentDTO.setThumbCount(thumbCount);
            commentDTO.setIsLike(isLike);
            commentDTOList.add(commentDTO);

        }
        return R.ok().put("data", commentDTOList);
    }

    private int getThumbCount(Comment comment) throws IOException {
        int thumbCount = 0;

        List<Comment> thumbList = commentDao.selectList(new QueryWrapper<Comment>()
                .eq("type", 1)
                .eq("to_uid", comment.getUid()));

        thumbCount = thumbList.size();

        String thumbScanKey = "*::" + comment.getUid();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash()
                .scan(POST_THUMB_KEY, ScanOptions.scanOptions().match(thumbScanKey).build());


        while (cursor.hasNext()) {
            thumbCount++;
            cursor.next();
        }

        cursor.close();

        return thumbCount;
    }

    private boolean isLike(Comment comment) {
        boolean isLike = false;

        if (StpUtil.isLogin()) {
            String loginIdAsString = StpUtil.getLoginIdAsString();

            String redisKey = getThumbOrCollectKey(loginIdAsString, comment.getUid());

            Object thumbRedis = redisUtil.hget(POST_THUMB_KEY, redisKey);

            if (thumbRedis == null) {
                Comment thumb = commentDao.selectOne(new QueryWrapper<Comment>()
                        .eq("to_uid", comment.getUid())
                        .eq("member_uid", loginIdAsString));
                if (thumb != null) isLike = true;
            } else if ((int) thumbRedis == 1) {
                isLike = true;
            }
        }
        return isLike;
    }


    private List<CommentDTO> getSonCommentPage(String firstCommentUid) throws IOException {
        List<Comment> commentList = commentDao.selectList(new QueryWrapper<Comment>()
                .eq("first_comment_uid", firstCommentUid)
                .eq("status", DEFAULT_STATUS));

        List<CommentDTO> commentDTOList = new ArrayList<>();

        CommentDTO commentDTO;

        for (Comment comment : commentList) {
            commentDTO = new CommentDTO();

            MemberDTO fromMember = memberFeignService.getMemberByUid(comment.getMemberUid());
            MemberDTO toMember = memberFeignService.getMemberByUid(comment.getToMemberUid());

            int thumbCount = getThumbCount(comment);

            boolean isLike = isLike(comment);
            commentDTO.setUid(comment.getUid());
            commentDTO.setFromMember(fromMember);
            commentDTO.setToMember(toMember);
            commentDTO.setContent(comment.getContent());
            commentDTO.setCreatedTime(comment.getCreatedTime());
            commentDTO.setThumbCount(thumbCount);
            commentDTO.setIsLike(isLike);
            commentDTOList.add(commentDTO);
        }

        return commentDTOList;

    }
}




