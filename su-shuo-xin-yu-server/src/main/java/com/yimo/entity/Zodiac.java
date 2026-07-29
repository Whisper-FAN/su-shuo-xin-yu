package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生肖实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("zodiac")
public class Zodiac extends BaseEntity {

    private String name;

    private String alias;

    private String imageUrl;

    private String description;

    private String personality;

    private String luckyColor;

    private String luckyNumber;

    private String element;

    private Integer sortOrder;

    private Long viewCount;

    private Integer status;
}
