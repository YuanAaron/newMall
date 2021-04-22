package cn.coderap.service.impl;

import cn.coderap.enums.ResponseEnum;
import cn.coderap.mapper.UserMapper;
import cn.coderap.pojo.User;
import cn.coderap.service.IUserService;
import cn.coderap.vo.ResponseVO;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * Created by yw
 * 2021/4/20
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseVO register(User user) {
//        error();
        //用户名不能重复
        Integer countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername>0) {
//            throw new RuntimeException("该用户名已注册！");
            return ResponseVO.error(ResponseEnum.USERNAME_EXIST);
        }
        //email不能重复
        Integer countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail>0) {
//            throw new RuntimeException("该邮箱已注册！");
            return ResponseVO.error(ResponseEnum.EMAIL_EXIST);
        }

        //MD5加密（Spring自带）
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        int count = userMapper.insertSelective(user);
        if (count==0) {
//            throw new RuntimeException("注册失败!");
            return ResponseVO.error(ResponseEnum.ERROR);
        }
        return ResponseVO.success();
    }

    void error() {
        throw new RuntimeException("意外错误");
    }

    @Override
    public ResponseVO<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        //用户名不存在
        if (user==null) {
            //返回用户名或密码错误（安全）
            return ResponseVO.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        if (!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            //返回用户名或密码错误（安全）
            return ResponseVO.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassword("");
        return ResponseVO.success(user);
    }
}
