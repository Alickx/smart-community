package cn.goroute.smart.auth.strategy.register;

import cn.goroute.smart.auth.model.vo.UserRegisterVO;
import com.hccake.ballcat.common.model.result.R;

import java.util.Map;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/17:21
 * @Description: 注册策略
 */
public class RegisterStrategy {

    private IRegister registerExecute;

    private final Map<String, IRegister> registerMap;

    public void setRegisterExecute(RegisterEnum registerEnum) {
        this.registerExecute = registerMap.get(registerEnum.getRegisterName());
    }

    public RegisterStrategy(Map<String, IRegister> registerMap) {
        this.registerMap = registerMap;
    }

    public R<Boolean> register(UserRegisterVO userRegisterVo) {
        return registerExecute.register(userRegisterVo);
    }


}
