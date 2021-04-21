package cn.coderap.controller;

import cn.coderap.dto.UserForm;
import cn.coderap.enums.ResponseEnum;
import cn.coderap.enums.RoleEnum;
import cn.coderap.pojo.User;
import cn.coderap.service.IUserService;
import cn.coderap.vo.ResponseVO;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * Created by yw
 * 2021/4/20
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 前端：x-www-form-urlencoded；后端：方式一是@RequestParam String username，方式二是User user。
     * 前端：raw+JSON；后端：@RequestBody User user
     */
    @PostMapping("/register")
    public ResponseVO register(@Valid @RequestBody UserForm userForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("注册提交的参数有误,{},{}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVO.error(ResponseEnum.PARAM_ERROR,bindingResult);
        }

        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setRole(RoleEnum.CUSTOMER.getCode());
        return userService.register(user);
    }
}
