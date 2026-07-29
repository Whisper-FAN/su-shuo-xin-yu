package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 泥塑作品实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("clay_sculpture")
public class ClaySculpture extends BaseEntity {

    private Long zodiacId;

    private String name;

    private String description;

    private String imageUrl;

    private String craftType;

    private String artist;

    private Integer year;

    private String material;

    private String sizeDesc;

    private Integer sortOrder;

    private Integer status;
}
