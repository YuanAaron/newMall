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

    public CartProductVO(Integer productId, Integer quantity, String productName, String productSubTitle, String productMainImage, BigDecimal productPrice, Integer productStatus, BigDecimal productTotalPrice, Integer productStock, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.productSubTitle = productSubTitle;
        this.productMainImage = productMainImage;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productTotalPrice = productTotalPrice;
        this.productStock = productStock;
        this.productSelected = productSelected;
    }
}
