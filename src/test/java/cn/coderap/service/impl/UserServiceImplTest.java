package cn.coderap.service.impl;

import cn.coderap.NewMallApplicationTest;
import cn.coderap.enums.RoleEnum;
import cn.coderap.pojo.User;
import cn.coderap.service.IUserService;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional //放在Test中数据不会进入数据库，这样既可以测功能，又不会在数据库中产生垃圾数据
public class UserServiceImplTest extends NewMallApplicationTest {

    @Resource
    private IUserService userService;

    @Test
    public void register() {
        User user = new User("zhangsan", "123456", "zhangsan@qq.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);
    }
}