package cn.goroute.smart.gateway.constant;

/**
 * @Author: Alickx
 * @Date: 2022/08/31/1:39
 * @Description: 常量类
 */
public class GatewayConstant {

    private GatewayConstant() {}

    public static class MemberRedisKey {

        private MemberRedisKey() {}

        /**
         * 用户权限
         */
        public static final String PERMISSION_LIST_KEY = "auth:permission:";

        /**
         * 用户角色
         */
        public static final String ROLE_LIST_KEY = "auth:role:";
    }

}
