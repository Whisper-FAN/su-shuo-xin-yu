package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户行为记录实体
 *
 * @author yimo-team
 */
@Data
@TableName("user_behavior")
public class UserBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String behaviorType;

    private Long targetId;

    private String targetType;

    private String extraData;

    private String ip;

    private String sessionId;

    private LocalDateTime createTime;
}
