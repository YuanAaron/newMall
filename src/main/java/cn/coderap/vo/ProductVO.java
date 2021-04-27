package cn.coderap.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by yw
 * 2021/4/25
 */
@Data
public class ProductVO {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private Integer status;

    private BigDecimal price;
}
