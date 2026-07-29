package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 合作伙伴实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("partner")
public class Partner extends BaseEntity {

    private String name;

    private String logoUrl;

    private String website;

    private String description;

    private String type;

    private Integer sortOrder;

    private Integer status;
}
