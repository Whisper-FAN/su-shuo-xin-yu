package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 每日统计实体
 *
 * @author yimo-team
 */
@Data
@TableName("statistics_daily")
public class StatisticsDaily implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime statDate;

    private Long pv;

    private Long uv;

    private Integer testCount;

    private Integer productClick;

    private Integer shareCount;

    private Integer newUserCount;

    private Long mostPopularZodiac;

    private java.math.BigDecimal totalRevenue;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
