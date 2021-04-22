package cn.coderap.exception;

import cn.coderap.enums.ResponseEnum;
import cn.coderap.vo.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
}
