package cn.goroute.smart.member.constant;

/**
 * @Author: Alickx
 * @Date: 2022/08/29/0:34
 * @Description:
 */
public class MemberConstant {

    private MemberConstant() {}

     public static class Register {

        private Register() {}

        public static final int REGISTER_ERROR_COUNT = 5;

        public static final int REGISTER_BAN_TIME = 10 * 60;

    }

}
