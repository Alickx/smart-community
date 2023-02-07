package cn.goroute.smart.risk.feign;

import cn.goroute.smart.common.config.FeignConfig;
import cn.goroute.smart.post.domain.Post;
import com.hccake.ballcat.common.model.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "smart-post", configuration = FeignConfig.class)
public interface FeignPostService {




	/**
	 * 内部服务间调用 - 根据id查询文章
	 * @param postId 文章id
	 * @return 文章实体类
	 */
	@GetMapping("/post/get/{postId}")
	R<Post> getById(@PathVariable("postId") Long postId);



}
