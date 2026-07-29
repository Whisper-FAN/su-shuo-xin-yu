package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生肖故事实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("zodiac_story")
public class ZodiacStory extends BaseEntity {

    private Long zodiacId;

    private String title;

    private String content;

    private String imageUrl;

    private String videoUrl;

    private Integer sortOrder;
}
