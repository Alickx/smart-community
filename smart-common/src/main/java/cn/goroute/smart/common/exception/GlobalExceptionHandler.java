package cn.goroute.smart.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import com.hccake.ballcat.common.model.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Alickx
 * @Date: 2023/02/14 22:19:41
 * @Description:
 */
@Order
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotLoginException.class)
	@ResponseStatus(HttpStatus.OK)
	public R<String> handleSaTokenException(Exception e, HttpServletRequest request) {
		return R.failed(ErrorCodeEnum.USER_NOT_LOGIN);
	}

}
