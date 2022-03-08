package cn.goroute.smart.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListDTO {

    private String uid;


    private String title;
    /**
     *
     */
    private String headImg;
    /**
     *
     */
    private String summary;


    private LocalDateTime createdTime;
    /**
     *
     */
    private Integer collectCount;
    /**
     *
     */
    private Integer thumbCount;
    /**
     *
     */
    private Integer clickCount;

    /**
     * 作者信息
     */
    private MemberDTO authorInfo;

    private Boolean isLike;

    private Boolean isCollect;

}
