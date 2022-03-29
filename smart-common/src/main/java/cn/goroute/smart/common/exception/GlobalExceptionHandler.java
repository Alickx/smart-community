package cn.goroute.smart.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.goroute.smart.common.utils.Result;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 请求参数缺失异常
     * @param request 请求
     * @param e 异常
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result missingServletRequestParameterExceptionHandler(HttpServletRequest request, MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException, uri:{}", request.getRequestURI());
        log.error("msg={}", e.getParameterName());
        return Result.error(BizCodeEnum.DEFAULT_PARAM.getCode(), "参数缺失异常: [" + e.getParameterName() + "]");
    }

    /**
     * 参数校验错误
     * @param request 请求
     * @param e 错误异常
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleParamCheckException(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException, uri:{}", request.getRequestURI());
        BindingResult bindingResult = e.getBindingResult();
        StringJoiner errMsgJoiner = new StringJoiner(",", "[", "]");
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String msg = objectError.getDefaultMessage();
                if (StringUtils.hasText(msg)) {
                    errMsgJoiner.add(msg);
                }
            }
        }
        log.error("msg={},uri={}", errMsgJoiner,request.getRequestURI());
        return Result.error(BizCodeEnum.VALID_EXCEPTION.getCode(), "参数检验失败: " + errMsgJoiner);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException e) {
        log.error("ConstraintViolationException, uri:{}", request.getRequestURI());
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringJoiner errMsgJoiner = new StringJoiner(",", "[", "]");
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            String errMsg = constraintViolation.getMessageTemplate();
            if (StringUtils.hasText(errMsg)) {
                errMsgJoiner.add(errMsg);
            }
        }
        log.warn("msg={}", errMsgJoiner);
        return Result.error(BizCodeEnum.VALID_EXCEPTION.getCode(), "参数校验失败: " + errMsgJoiner);
    }

    /**
     * ServiceException
     * @param request 请求
     * @param e {@link ServiceException}
     * @return 异常信息
     */
    @ExceptionHandler(ServiceException.class)
    public Result apiExceptionHandler(HttpServletRequest request, ServiceException e) {
        log.info("ServiceException, uri:{}", request.getRequestURI());
        String message = e.getMessage();
        log.error("message={}", message);
        return Result.error(e.getMessage());
    }

    /**
     * 请求方法错误异常
     * @param request 请求
     * @param e 异常
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request,HttpRequestMethodNotSupportedException e){

        log.info("错误的请求方法,请求ip={},请求uri={},msg=>{}", ServletUtil.getClientIP(request),request.getRequestURI(),e.getMessage());
        return Result.error(BizCodeEnum.ERROR_REQUEST_METHOD.getCode(),BizCodeEnum.ERROR_REQUEST_METHOD.getMessage());
    }


    /**
     * 登录认证异常错误
     * @param nle 异常
     * @param request 请求
     * @param response 响应
     * @return
     * @throws Exception
     */
    @ExceptionHandler(NotLoginException.class)
    public Result handlerNotLoginException(NotLoginException nle, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 判断场景值，定制化异常信息
        String message = "";
        if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "请登录后再访问该资源！";
        }
        else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "登录无效，请重新登录！";
        }
        else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "登录过期，请重新登录！";
        }
        else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "您的账号在别处登录！";
        }
        else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "您的账号已被强制下线！";
        }
        else {
            message = "当前未登录账号，请登陆后再访问！";
        }
        // 返回给前端
        return Result.error(BizCodeEnum.NOT_LOGIN_EXCEPTION.getCode(),message);
    }

    /**
     * 最终异常处理方法
     * @param request 请求
     * @param e 异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("Exception, uri:{}", request.getRequestURI(), e);
        return Result.error();
    }

}