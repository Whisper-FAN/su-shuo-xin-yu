package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends BaseEntity {

    private String openid;

    private String nickname;

    private String avatar;

    private String phone;

    private Integer gender;

    private String role;

    private String password;

    private Integer birthYear;

    private String province;

    private String city;

    private LocalDateTime lastLoginAt;
}
