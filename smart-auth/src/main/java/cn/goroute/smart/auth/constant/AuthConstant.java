package cn.goroute.smart.auth.constant;

/**
 * @Author: Alickx
 * @Date: 2022/08/29/23:01
 * @Description: 常量类
 */
public class AuthConstant {

    private AuthConstant() {}

    /**
     * 注册常量
     */
    public static class Register {

        private Register() {}

        public static final int REGISTER_ERROR_COUNT = 5;

        public static final int REGISTER_BAN_TIME = 10 * 60;

    }

}
