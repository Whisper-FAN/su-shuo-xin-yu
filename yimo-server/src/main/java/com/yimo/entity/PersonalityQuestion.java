package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 性格测试题目实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("personality_question")
public class PersonalityQuestion extends BaseEntity {

    private String questionText;

    private String dimension;

    private String positiveScore;

    private String negativeScore;

    private Integer sortOrder;

    private String questionType;

    private Integer status;
}
