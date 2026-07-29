package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 性格测试选项实体
 *
 * @author yimo-team
 */
@Data
@TableName("personality_option")
public class PersonalityOption implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long questionId;

    private String optionText;

    private Integer score;

    private Integer sortOrder;

    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
