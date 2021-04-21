package cn.coderap.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色 0-管理员，1-普通用户
 * Created by yw
 * 2021/4/20
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(0),
    CUSTOMER(1);

    private Integer code;
}
