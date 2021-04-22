package cn.coderap.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by yw
 * 2021/4/21
 */
@Data
public class UserLoginForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
