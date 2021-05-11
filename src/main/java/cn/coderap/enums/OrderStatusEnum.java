package cn.coderap.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态 0-已取消，10-未付款-20-已付款，40-已发货，50-交易成功，60-交易关闭
 * Created by yw
 * 2021/5/10
 */
@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
    CANCEL(0,"已取消"),
    NO_PAY(10,"未付款"),
    PAID(20,"已付款"),
    SHIPPED(40,"已发货"),
    TRADE_SUCCESS(50,"交易成功"),
    TRADE_CLOSE(60,"交易关闭");

    private Integer code;
    private String msg;
}
