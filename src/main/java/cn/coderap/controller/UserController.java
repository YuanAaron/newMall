package cn.coderap.controller;

import cn.coderap.consts.NewMallConsts;
import cn.coderap.dto.UserLoginForm;
import cn.coderap.dto.UserRegisterForm;
import cn.coderap.enums.ResponseEnum;
import cn.coderap.enums.RoleEnum;
import cn.coderap.pojo.User;
import cn.coderap.service.IUserService;
import cn.coderap.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

/**
 * Created by yw
 * 2021/4/20
 */
@Slf4j
@RestController
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 前端：x-www-form-urlencoded；后端：方式一是@RequestParam String username，方式二是User user。
     * 前端：raw+JSON；后端：@RequestBody User user
     */
    @PostMapping("/user/register")
    public ResponseVO register(@Valid @RequestBody UserRegisterForm userRegisterForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("注册提交的参数有误,{},{}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVO.error(ResponseEnum.PARAM_ERROR,bindingResult);
        }

        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        user.setRole(RoleEnum.CUSTOMER.getCode());
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVO<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                            BindingResult bindingResult,
                            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return ResponseVO.error(ResponseEnum.PARAM_ERROR,bindingResult);
        }

        ResponseVO<User> responseVO = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        //设置session: session保存在内存里，重启项目会丢失。
        //一旦获取了已登录用户的sessionId，在任何地方都可以登录，不安全
        //改进版：token+redis（老师说这里的token就是用的sessionId，我的想法是sessionId不安全，考虑使用sessionId加盐，也许这就是token比sessionId好的地方吧）
        session.setAttribute(NewMallConsts.CURRENT_USER, responseVO.getData());
        log.info("/login sessionId={}", session.getId());
        return responseVO;
    }

    /**
     * 注意点：
     * 如果使用localhost:8088/user/login登录，也要使用localhost:8088/user访问用户信息；
     * 如果使用127.0.0.1:8088/user/login登录，也要使用127.0.0.1:8088/user访问用户信息；
     * 解释：第一种情况JESSIONID的Domain是localhost，第二种情况JESSIONID的Domain是127.0.0.1，如果使用第一种方法登录，用第二种方法
     * 访问用户信息，就跨域了，就会显示未登录
     * @param session
     * @return
     */
    @GetMapping("/user")
    public ResponseVO<User> userInfo(HttpSession session) {
        log.info("/user sessionId={}", session.getId());
        User user = (User)session.getAttribute(NewMallConsts.CURRENT_USER);
        if (user == null) {
            return ResponseVO.error(ResponseEnum.NEED_LOGIN);
        }
        return ResponseVO.success(user);
    }

    /**
     * TODO /user/logout、/user以及将来查订单和查看购物车等，都会判断用户是否登录，可以通过拦截器来判断登录状态
     * @param session
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseVO<User> logout(HttpSession session) {
        log.info("/user/logout sessionId={}", session.getId());
        User user = (User)session.getAttribute(NewMallConsts.CURRENT_USER);
        if (user == null) {
            return ResponseVO.error(ResponseEnum.NEED_LOGIN);
        }
        session.removeAttribute(NewMallConsts.CURRENT_USER);
        return ResponseVO.successByMsg("退出成功");
    }
}
