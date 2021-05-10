package cn.coderap.pojo.vo;

import cn.coderap.pojo.Shipping;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yw
 * 2021/5/10
 */
@Data
public class OrderVO {
    private Long orderNo;
    private BigDecimal payment;
    private Integer paymentType;
    private Integer postage;
    private Integer status;
    private Date paymentTime;
    private Date sendTime;
    private Date endTime;
    private Date closeTime;
    private Date createTime;
    private List<OrderItemVO> orderItemVoList;
    private Integer shippingId;
    private Shipping shippingVO;
}
