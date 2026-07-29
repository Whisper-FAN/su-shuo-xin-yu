package com.yimo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 团队成员实体
 *
 * @author yimo-team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("team_member")
public class TeamMember extends BaseEntity {

    private String name;

    private String avatarUrl;

    private String title;

    private String department;

    private String description;

    private Integer sortOrder;

    private Integer isCore;

    private Integer status;
}
