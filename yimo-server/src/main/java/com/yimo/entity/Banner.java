package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 轮播图实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("banner")
public class Banner extends BaseEntity {

    private String title;

    private String imageUrl;

    private String linkUrl;

    private Integer sortOrder;

    private Integer status;
}
