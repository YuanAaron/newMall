package cn.coderap.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付类型：1-线上支付 2-货到付款
 */
@AllArgsConstructor
@Getter
public enum PaymentTypeEnum {
    PAY_ONLINE(1),
    PAY_OFFLINE(2);

    private Integer code;
}
