package cn.coderap.pojo.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by yw
 * 2021/4/30
 */
@Data
public class ShippingForm {
    @NotBlank
    private String receiverName;
    @NotBlank
    private String receiverPhone;
    @NotBlank
    private String receiverMobile;
    @NotBlank
    private String receiverProvince;
    @NotBlank
    private String receiverCity;
    @NotBlank
    private String receiverDistrict;
    @NotBlank
    private String receiverAddress;
    @NotBlank
    private String receiverZip;
}
