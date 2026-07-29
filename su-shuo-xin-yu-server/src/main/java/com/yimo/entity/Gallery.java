package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图库实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gallery")
public class Gallery extends BaseEntity {

    private String title;

    private String imageUrl;

    private String thumbUrl;

    private String category;

    private String description;

    private Integer sortOrder;

    private Integer status;
}
