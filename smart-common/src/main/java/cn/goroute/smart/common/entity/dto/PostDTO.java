package cn.goroute.smart.common.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private String uid;


    private String title;
    /**
     *
     */
    private String content;
    /**
     *
     */
    private String headImg;
    /**
     * 0 = 不公布  1 = 公布
     */
    private String isPublish;
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

    private Integer status;

}
