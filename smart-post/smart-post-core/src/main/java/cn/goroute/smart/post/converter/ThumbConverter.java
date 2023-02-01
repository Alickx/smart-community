package cn.goroute.smart.post.converter;

import cn.goroute.smart.post.model.dto.ThumbDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: Alickx
 * @Date: 2023/01/07/11:41
 * @Description:
 */
@Mapper
public interface ThumbConverter {

    ThumbConverter INSTANCE = Mappers.getMapper(ThumbConverter.class);

    /**
     * po 转 dto
     */
    cn.goroute.smart.post.domain.Thumb dtoToPo(ThumbDTO thumbDTO);

}
