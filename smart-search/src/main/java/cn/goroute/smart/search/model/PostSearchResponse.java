package cn.goroute.smart.search.model;

import lombok.Data;

import java.util.List;

@Data
public class PostSearchResponse {

    private List<PostEsModel> postList;

    private Integer pageNum;

    private Long total;

    private Integer totalPages;

}
