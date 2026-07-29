package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 文章实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article")
public class Article extends BaseEntity {

    private Long categoryId;

    private String title;

    private String summary;

    private String coverUrl;

    private String content;

    private String author;

    private String source;

    private Long viewCount;

    private Long likeCount;

    private Integer status;

    private Integer isTop;

    private LocalDateTime publishTime;
}
