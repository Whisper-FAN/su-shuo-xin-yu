package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 产品实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product")
public class Product extends BaseEntity {

    private Long categoryId;

    private Long zodiacId;

    private String name;

    private String description;

    private String detail;

    private String imageUrl;

    private String images;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private Integer stock;

    private Integer sales;

    private Long viewCount;

    private Integer isHot;

    private Integer isRecommend;

    private String tags;

    private String productTier;

    private Integer sortOrder;

    private Integer status;
}
