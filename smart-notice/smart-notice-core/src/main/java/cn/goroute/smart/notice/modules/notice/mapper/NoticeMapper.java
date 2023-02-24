package cn.goroute.smart.notice.modules.notice.mapper;

import cn.goroute.smart.notice.domain.dto.NoticeCountVO;
import cn.goroute.smart.notice.domain.dto.NoticeMessageDTO;
import cn.goroute.smart.notice.domain.entity.NoticeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author llwst
 * @description 针对表【t_notice(站内通知)】的数据库操作Mapper
 * @createDate 2023-02-14 14:14:44
 * @Entity cn.goroute.smart.notification.domain.Notice
 */
public interface NoticeMapper extends BaseMapper<NoticeEntity> {

	/**
	 * 查询某个类型的通知是否已经存在
	 * @param senderId 发送者用户id
	 * @param receiverId 接受者用户id
	 * @param msgType 消息类型
	 * @param targetId 消息对象id
	 * @return 数量 0不存在 1存在
	 */
	NoticeEntity queryNoticeIsExist(@Param("senderId") Long senderId
			, @Param("receiverId") Long receiverId, @Param("msgType") Integer msgType, @Param("targetId") Long targetId);


	/**
	 * 查询某个用户按类型区分的通知数量
	 * @param receiverId 用户id
	 * @param status 通知状态 0未读 1已读
	 * @return
	 */
	List<NoticeCountVO> queryNoticeCount(@Param("receiverId") Long receiverId, @Param("status") Integer status);


	IPage<NoticeMessageDTO> pageNotice(@Param("receiverId") long receiverId, @Param("msgType") Integer msgType, Page<NoticeMessageDTO> page);

	void updateNoticeStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);
}




