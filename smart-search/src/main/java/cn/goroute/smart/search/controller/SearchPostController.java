package cn.goroute.smart.search.controller;

import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.search.model.SearchParam;
import cn.goroute.smart.search.service.PostSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchPostController {

    @Autowired
    PostSearchService postSearchService;


    @PostMapping("/post")
    public R searchPost(@RequestBody SearchParam searchParam){
        return postSearchService.search(searchParam);
    }

}
