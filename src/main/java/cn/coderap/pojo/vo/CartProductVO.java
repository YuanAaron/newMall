package cn.coderap.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by yw
 * 2021/4/27
 */
@Data
public class CartProductVO {
    private Integer productId;
    private Integer quantity;
    private String productName;
    private String productSubTitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    private Boolean productSelected;
}
