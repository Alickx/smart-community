package cn.goroute.smart.auth.strategy.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/17:27
 * @Description: 注册枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RegisterEnum {

    Email(0,"EmailRegister"),
    Phone(1,"PhoneRegister"),
    ;

    /**
     * 注册类型
     */
    private Integer registerType;

    /**
     * 注册名称
     */
    private String registerName;

    public static RegisterEnum getRegisterEnum(Integer registerType){
        for (RegisterEnum registerEnum : RegisterEnum.values()) {
            if (registerEnum.getRegisterType().equals(registerType)){
                return registerEnum;
            }
        }
        return null;
    }

}
