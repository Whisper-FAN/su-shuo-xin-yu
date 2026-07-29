package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品分类实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_category")
public class ProductCategory extends BaseEntity {

    private String name;

    private String description;

    private String iconUrl;

    private Integer sortOrder;

    private Integer status;
}
