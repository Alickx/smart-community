package cn.goroute.smart.post.modules.thumb.async;

import cn.goroute.smart.post.modules.thumb.converter.ThumbConverter;
import cn.goroute.smart.post.domain.entity.ThumbEntity;
import cn.goroute.smart.post.domain.form.ThumbForm;
import cn.goroute.smart.post.modules.thumb.strategy.thumb.ThumbStrategy;
import cn.goroute.smart.post.modules.thumb.strategy.thumb.ThumbStrategyEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: Alickx
 * @Date: 2023/02/14 17:58:25
 * @Description:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ThumbAsyncService {

	@Async
	public void thumbHandle(ThumbForm thumbForm, Long userId, boolean saveFlag) {

		ThumbEntity thumbEntity = ThumbConverter.INSTANCE.formToEntity(thumbForm);
		thumbEntity.setUserId(userId);

		// 执行点赞策略
		ThumbStrategy strategy = ThumbStrategyEnum.getStrategyByType(thumbEntity.getType());
		if (strategy == null) {
			log.error("未找到对应的点赞策略:[{}]", thumbEntity);
			return;
		}
		if (saveFlag) {
			strategy.saveThumb(thumbEntity);
		} else {
			strategy.cancelThumb(thumbEntity);
		}



	}

}
