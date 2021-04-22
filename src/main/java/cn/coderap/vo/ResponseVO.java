package cn.coderap.vo;

import cn.coderap.enums.ResponseEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * Created by yw
 * 2021/4/21
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL) //去掉json返回值中value为null的字段
public class ResponseVO<T> {
    private Integer code;
    private String msg;
    private T data;

    public ResponseVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseVO(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ResponseVO success() {
        return new ResponseVO<>(ResponseEnum.SUCESS.getCode(),ResponseEnum.SUCESS.getDesc());
    }

    public static <T> ResponseVO successByMsg(String msg) {
        return new ResponseVO<>(ResponseEnum.SUCESS.getCode(),msg);
    }

    public static <T> ResponseVO success(T data) {
        return new ResponseVO<>(ResponseEnum.SUCESS.getCode(),data);
    }

    public static <T> ResponseVO error(ResponseEnum responseEnum) {
        return new ResponseVO<>(responseEnum.getCode(),responseEnum.getDesc());
    }

    public static <T> ResponseVO error(ResponseEnum responseEnum,String msg) {
        return new ResponseVO<>(responseEnum.getCode(),msg);
    }

    public static <T> ResponseVO error(ResponseEnum responseEnum, BindingResult bindingResult) {
        return new ResponseVO<>(responseEnum.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getField()
                + " " + bindingResult.getFieldError().getDefaultMessage());
    }
}
