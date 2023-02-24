package cn.goroute.smart.notice.modules.notice.converter;

import cn.goroute.smart.notice.domain.dto.NoticeMessageDTO;
import cn.goroute.smart.notice.domain.vo.NoticeMessageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeMessageConverter {

    NoticeMessageConverter INSTANCE = Mappers.getMapper(NoticeMessageConverter.class);


    @Mapping(target = "sender", ignore = true)
    NoticeMessageVO dtoToVO(NoticeMessageDTO noticeMessageDTO);

}
