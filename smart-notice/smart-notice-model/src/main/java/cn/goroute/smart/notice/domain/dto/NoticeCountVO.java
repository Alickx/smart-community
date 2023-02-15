package cn.goroute.smart.notice.domain.dto;

import lombok.Data;

/**
 * @author: Alickx
 * @Date: 2023/02/14 21:32:22
 * @Description:
 */
@Data
public class NoticeCountVO {

	/**
	 * 通知数量
	 */
	private Integer noticeCount;

	/**
	 * 通知类型
	 */
	private Integer noticeType;

}
