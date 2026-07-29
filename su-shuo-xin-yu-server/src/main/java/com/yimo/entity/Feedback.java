package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 反馈实体
 *
 * @author yimo-team
 */
@Data
@TableName("feedback")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String content;

    private String contact;

    private String type;

    private Integer isHandled;

    private String handleNote;

    private Integer deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
