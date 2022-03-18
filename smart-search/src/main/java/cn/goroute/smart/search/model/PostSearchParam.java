package cn.goroute.smart.search.model;

import lombok.Data;

@Data
public class PostSearchParam {

    private String keyword;

    private Integer curPage;

    private String sortType;

}
