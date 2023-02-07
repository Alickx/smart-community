package cn.goroute.smart.search.feign;

import cn.goroute.smart.common.config.FeignConfig;
import cn.goroute.smart.post.domain.Category;
import cn.goroute.smart.post.domain.Tag;
import com.hccake.ballcat.common.model.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "smart-post", configuration = FeignConfig.class)
public interface FeignPostService {

    @GetMapping("/tag/query")
    R<Tag> queryTagById(Long id);


    @GetMapping("/category/query")
    R<Category> queryCategoryById(Long id);

}
