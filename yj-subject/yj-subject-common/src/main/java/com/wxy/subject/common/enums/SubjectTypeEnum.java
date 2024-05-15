package com.wxy.subject.common.enums;

import lombok.Getter;

/**
 * @program: YunJuClub
 * @description: 题目类型枚举类
 * @author: 32115
 * @create: 2024-03-21 08:47
 */

@Getter
public enum SubjectTypeEnum {

    RADIO(1, "单选"),
    MULTIPLE(2, "多选"),
    JUDGE(3, "判断"),
    BRIEF(4, "简答");

    // 状态码
    private final int code;

    // 提示信息
    private final String msg;

    SubjectTypeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    // 根据传入的code找到对应的枚举类型
    public static SubjectTypeEnum getByCode(int code){
        for (SubjectTypeEnum subjectTypeCode : SubjectTypeEnum.values()){
            if (subjectTypeCode.code == code){
                return subjectTypeCode;
            }
        }
        return null;
    }
}
