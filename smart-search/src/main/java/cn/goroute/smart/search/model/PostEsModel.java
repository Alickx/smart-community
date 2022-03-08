package cn.goroute.smart.search.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostEsModel {
    /**
     *
     */
    private String uid;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String content;
    /**
     * 0 = 不公布  1 = 公布
     */
    private String isPublish;
    /**
     *
     */
    private String memberUid;
    /**
     *
     */
    private String summary;
    /**
     *
     */
    private LocalDateTime updateTime;
    /**
     *
     */
    private LocalDateTime createdTime;

}
