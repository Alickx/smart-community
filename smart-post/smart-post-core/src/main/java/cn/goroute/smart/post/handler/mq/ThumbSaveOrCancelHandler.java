package cn.goroute.smart.post.handler.mq;

import cn.goroute.smart.post.converter.ThumbConverter;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.model.dto.ThumbDTO;
import cn.goroute.smart.post.strategy.thumb.ThumbStrategy;
import cn.goroute.smart.post.strategy.thumb.ThumbStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/01/07/11:08
 * @Description: 创建点赞缓存，更新点赞数
 */
@Component
@Slf4j
public class ThumbSaveOrCancelHandler {

	@EventListener(ThumbDTO.class)
	public void handle(ThumbDTO thumbDTO) {
		Thumb thumb = ThumbConverter.INSTANCE.dtoToPo(thumbDTO);
		// 执行点赞策略
		ThumbStrategy strategy = ThumbStrategyEnum.getStrategyByType(thumb.getType());
		if (strategy == null) {
			log.error("点赞业务监听者未找到对应的点赞策略:[{}]", thumb);
			return;
		}
		if (thumbDTO.getIsSave()) {
			strategy.saveThumb(thumb);
		} else {
			strategy.cancelThumb(thumb);
		}
	}

}
