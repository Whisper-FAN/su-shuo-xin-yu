package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章分类实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article_category")
public class ArticleCategory extends BaseEntity {

    private String name;

    private String description;

    private Integer sortOrder;

    private Integer status;
}
