package com.yimo.enums;

import lombok.Getter;

/**
 * 通用状态枚举
 *
 * @author yimo-team
 */
@Getter
public enum Status {
    DISABLED(0, "禁用"),
    ENABLED(1, "启用");

    private final Integer code;
    private final String desc;

    Status(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
