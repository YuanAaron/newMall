package cn.coderap.pojo.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by yw
 * 2021/4/27
 */
@Data
public class CartAddForm {
    @NotNull
    private Integer productId;
    private Boolean selected = true;
}
