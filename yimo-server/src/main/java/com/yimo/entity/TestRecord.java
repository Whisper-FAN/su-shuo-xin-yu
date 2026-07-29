package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试记录实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("test_record")
public class TestRecord extends BaseEntity {

    private Long userId;

    private Long zodiacId;

    private Long resultId;

    private String answers;

    private String dimensionScore;

    private Integer shareCount;

    private Integer testDuration;
}
