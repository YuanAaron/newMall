package cn.coderap.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by yw
 * 2021/4/21
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    ERROR(-1,"服务端异常"),
    SUCESS(0,"成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USERNAME_EXIST(2,"用户名已存在"),
    EMAIL_EXIST(3,"邮箱已存在"),
    PARAM_ERROR(4,"参数错误"),
    NEED_LOGIN(10,"用户未登录，请先登录"),
    USERNAME_OR_PASSWORD_ERROR(11,"用户名或密码错误"),
    PRODUCT_OFF_SALE_OR_DELETE(12,"商品下架或删除");

    private Integer code;
    private String desc;
}
