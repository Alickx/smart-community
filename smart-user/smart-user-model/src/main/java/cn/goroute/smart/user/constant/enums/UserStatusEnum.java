package cn.goroute.smart.user.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    NORMAL(0, "正常"),

    ACTIVATE(1, "禁用"),


    ;


    private final Integer code;

    private final String desc;

}
