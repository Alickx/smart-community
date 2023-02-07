package cn.goroute.smart.search.controller;

import cn.goroute.smart.search.model.dto.PostIndexDTO;
import cn.goroute.smart.search.service.PostIndexService;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Alickx
 * @Date: 2023/01/30/23:39
 * @Description:
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/search/post")
public class PostSearchController {

    private final PostIndexService postIndexService;

    @GetMapping
    public R<PageResult<PostIndexDTO>> pageByKeyword(PageParam pageParam, @RequestParam("keyword") String keyword) {
        return postIndexService.pageByKeyword(pageParam, keyword);
    }

}
