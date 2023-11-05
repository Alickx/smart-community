package cn.goroute.smart.notice.modules.notice.controller;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.notice.domain.dto.NoticeCountVO;
import cn.goroute.smart.notice.domain.vo.NoticeMessageVO;
import cn.goroute.smart.notice.modules.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Alickx
 * @Date: 2023/02/14 14:12:28
 * @Description: 站内通知
 */
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {


	private final NoticeService noticeService;

	/**
	 * 查询未读通知总数
	 * @return 各未读通知数量
	 */
	@GetMapping("/queryNoticeCount")
	public R<List<NoticeCountVO>> queryNoticeCount() {
		return noticeService.queryNoticeCount();
	}

	/**
	 * 根据通知类型查询未读通知
	 * @param type 类型
	 * @param pageParam 分页参数
	 * @return
	 */
	@GetMapping("/pageNotice/{type}")
	public R<PageResult<NoticeMessageVO>> pageNotice(@PathVariable("type") Integer type, PageParam pageParam) {
		return noticeService.pageNotice(type,pageParam);
	}


}
