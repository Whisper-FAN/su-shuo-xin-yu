package com.yimo.enums;

import lombok.Getter;

/**
 * 人格维度枚举
 *
 * @author yimo-team
 */
@Getter
public enum DimensionType {
    EI("E/I", "能量来源", "外向/内向"),
    SN("S/N", "信息获取方式", "感觉/直觉"),
    TF("T/F", "决策方式", "思考/情感"),
    JP("J/P", "生活态度", "判断/感知"),
    VALUE("VALUE", "价值观倾向", "成就型/和谐型/探索型/关系型");

    private final String code;
    private final String name;
    private final String desc;

    DimensionType(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
