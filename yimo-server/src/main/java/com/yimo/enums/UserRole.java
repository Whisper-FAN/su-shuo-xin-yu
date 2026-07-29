package com.yimo.enums;

import lombok.Getter;

/**
 * 用户角色枚举
 *
 * @author yimo-team
 */
@Getter
public enum UserRole {
    VISITOR("visitor", "访客"),
    ADMIN("admin", "管理员");

    private final String code;
    private final String desc;

    UserRole(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
