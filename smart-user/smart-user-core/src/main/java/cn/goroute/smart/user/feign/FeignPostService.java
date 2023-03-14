package cn.goroute.smart.user.feign;

import cn.goroute.smart.common.config.FeignConfig;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.post.domain.vo.PostAbbreviationVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2023/03/11/21:50
 * @Description:
 */
@FeignClient(name = "smart-post", configuration = FeignConfig.class)
public interface FeignPostService {

	/**
	 * 批量查询文章缩略信息
	 * @param postIds 文章id集合
	 * @return 查询结果
	 */
	@GetMapping("/post/batch/info")
	R<List<PostAbbreviationVO>> batchInfo(@RequestParam("postIds") List<Long> postIds);


	@GetMapping("/post/query/isExist")
	R<Boolean> queryIsExist(@RequestParam("postId") Long postId);

}
