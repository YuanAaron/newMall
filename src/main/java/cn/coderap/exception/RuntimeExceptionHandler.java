package cn.coderap.exception;

import cn.coderap.enums.ResponseEnum;
import cn.coderap.pojo.vo.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

/**
 * Created by yw
 * 2021/4/21
 */
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //本项目用不到这一点
    public ResponseVO handle(RuntimeException e) {
        return ResponseVO.error(ResponseEnum.ERROR,e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVO userLoginHandle() {
        return ResponseVO.error(ResponseEnum.NEED_LOGIN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVO methodArgumentNotValidHandle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ResponseVO.error(ResponseEnum.PARAM_ERROR,Objects.requireNonNull(bindingResult.getFieldError()).getField()
                + " " + e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
