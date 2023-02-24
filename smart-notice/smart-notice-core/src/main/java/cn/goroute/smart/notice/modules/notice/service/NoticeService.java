package cn.goroute.smart.notice.modules.notice.service;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.notice.domain.dto.NoticeCountVO;
import cn.goroute.smart.notice.domain.entity.NoticeEntity;
import cn.goroute.smart.notice.domain.vo.NoticeMessageVO;
import cn.goroute.smart.post.domain.dto.CommentDTO;
import cn.goroute.smart.post.domain.dto.ThumbDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author alickx
* @description 针对表【t_notice(站内通知)】的数据库操作Service
* @createDate 2023-02-14 14:14:44
*/
public interface NoticeService extends IService<NoticeEntity> {

	void saveThumbNotice(ThumbDTO thumbDTO);

	void saveCommentNotice(CommentDTO commentDTO);

	R<List<NoticeCountVO>> queryNoticeCount();

	R<PageResult<NoticeMessageVO>> pageNotice(Integer type, PageParam pageParam);
}
