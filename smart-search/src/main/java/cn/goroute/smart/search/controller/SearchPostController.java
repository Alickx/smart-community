package cn.goroute.smart.search.controller;

import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.search.model.PostSearchParam;
import cn.goroute.smart.search.service.PostSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Alickx
 */
@RestController
@RequestMapping("smart/search")
public class SearchPostController {

    @Autowired
    PostSearchService postSearchService;


    @PostMapping("/post")
    public Result searchPost(@RequestBody PostSearchParam postSearchParam) {
        return postSearchService.search(postSearchParam);
    }


    @GetMapping("/delete")
    public void deleteSearchPost(@RequestParam String uid) throws IOException {
        postSearchService.deleteSearchPost(uid);
    }

}
