package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 性格测试结果实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("personality_result")
public class PersonalityResult extends BaseEntity {

    private Long zodiacId;

    private String title;

    private String description;

    private String personalityTags;

    private String strengths;

    private String weaknesses;

    private String careerAdvice;

    private String relationshipAdvice;

    private String shareImageUrl;

    private Integer minScore;

    private Integer maxScore;
}
