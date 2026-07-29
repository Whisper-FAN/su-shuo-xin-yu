package com.yimo.enums;

import lombok.Getter;

/**
 * 性别枚举
 *
 * @author yimo-team
 */
@Getter
public enum Gender {
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private final Integer code;
    private final String desc;

    Gender(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
