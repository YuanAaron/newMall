package cn.coderap.pojo.bo;

import lombok.Data;

/**
 * Created by yw
 * 2021/4/29
 */
@Data
public class CartUpdateForm {
    private Integer quantity; //非必填
    private Boolean selected; //非必填
}
