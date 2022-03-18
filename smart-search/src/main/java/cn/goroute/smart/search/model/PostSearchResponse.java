package cn.goroute.smart.search.model;

import cn.goroute.smart.common.entity.dto.PostListDTO;
import lombok.Data;

import java.util.List;

@Data
public class PostSearchResponse {

    private List<PostListDTO> postList;

    private Integer pageNum;

    private Long total;

    private Integer totalPages;

}
