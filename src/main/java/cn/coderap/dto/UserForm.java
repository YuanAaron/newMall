package cn.coderap.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @NotEmpty、@NotBlank、@NotNull三种注解的区别:
 * @NotEmpty 用在集合类上面，加了@NotEmpty的String类、Collection、Map、数组，是不能为null或者长度为0的(String Collection Map的isEmpty()方法)
 * @NotBlank只用于String,不能为null且trim()之后size>0
 * @NotNull:不能为null，但可以为empty,没有Size的约束
 * Created by yw
 * 2021/4/21
 */
@Data
public class UserForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}
