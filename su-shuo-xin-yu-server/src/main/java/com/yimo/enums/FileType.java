package com.yimo.enums;

import lombok.Getter;

/**
 * 文件类型枚举
 *
 * @author yimo-team
 */
@Getter
public enum FileType {
    IMAGE("image", "图片"),
    VIDEO("video", "视频"),
    DOCUMENT("document", "文档"),
    OTHER("other", "其他");

    private final String code;
    private final String desc;

    FileType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
