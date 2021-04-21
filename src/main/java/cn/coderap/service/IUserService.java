package cn.coderap.service;

import cn.coderap.pojo.User;
import cn.coderap.vo.ResponseVO;

public interface IUserService {

    /**
     * 注册
     */
    ResponseVO register(User user);

    /**
     * 登录
     */
}
