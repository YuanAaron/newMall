package cn.coderap.service.impl;

import cn.coderap.NewMallApplicationTest;
import cn.coderap.enums.ResponseEnum;
import cn.coderap.enums.RoleEnum;
import cn.coderap.pojo.User;
import cn.coderap.service.IUserService;
import cn.coderap.pojo.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional //放在Test中数据不会进入数据库，这样既可以测功能，又不会在数据库中产生垃圾数据
public class UserServiceImplTest extends NewMallApplicationTest {

    public static final String USERNAME = "wangwu";
    public static final String PASSWORD = "123456";

    @Resource
    private IUserService userService;

    @Before
    public void register() {
        User user = new User(USERNAME, PASSWORD, "wangwu@qq.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);
    }

    @Test
    public void login() {
        ResponseVO<User> responseVO = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCESS.getCode(), responseVO.getCode());
    }
}