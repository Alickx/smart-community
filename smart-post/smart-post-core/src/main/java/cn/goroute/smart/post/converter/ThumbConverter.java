package cn.goroute.smart.post.converter;

import cn.goroute.smart.post.domain.dto.ThumbDTO;
import cn.goroute.smart.post.domain.entity.ThumbEntity;
import cn.goroute.smart.post.domain.form.ThumbForm;
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
    ThumbEntity dtoToPo(ThumbDTO thumbDTO);

	ThumbDTO formToDto(ThumbForm thumbForm);

	ThumbEntity formToEntity(ThumbForm thumbForm);

	ThumbDTO poToDto(ThumbEntity thumbEntity);
}
