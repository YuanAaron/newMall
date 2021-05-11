package cn.coderap.pojo.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by yw
 * 2021/5/11
 */
@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}
