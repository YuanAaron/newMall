package cn.coderap.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yw
 * 2021/4/27
 */
@Data
public class CartVO {
    private List<CartProductVO> cartProductVoList;
    private Boolean selectAll;
    private BigDecimal cartTotalPrice;
    private Integer cartTotalQuantity;
}
