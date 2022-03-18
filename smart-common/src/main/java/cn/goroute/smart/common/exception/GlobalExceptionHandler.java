package cn.goroute.smart.common.exception;

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
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 参数缺失异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result missingServletRequestParameterExceptionHandler(HttpServletRequest request, MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException, uri:{}", request.getRequestURI());
        log.error("msg={}", e.getParameterName());
        return Result.error(BizCodeEnum.DEFAULT_PARAM.getCode(), "参数缺失异常: [" + e.getParameterName() + "]");
    }

    // 参数校验失败异常
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

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result HttpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request,HttpRequestMethodNotSupportedException e){

        log.info("错误的请求方法,请求ip={},请求uri={},msg=>{}", ServletUtil.getClientIP(request),request.getRequestURI(),e.getMessage());
        return Result.error(BizCodeEnum.ERROR_REQUEST_METHOD.getCode(),BizCodeEnum.ERROR_REQUEST_METHOD.getMessage());
    }

    // 系统异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("Exception, uri:{}", request.getRequestURI(), e);
        return Result.error();
    }

}