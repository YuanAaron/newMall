package cn.coderap.config;

import cn.coderap.consts.NewMallConsts;
import cn.coderap.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yw
 * 2021/4/22
 */
@Slf4j
@Component
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入preHandle...");
        User user = (User)request.getSession().getAttribute(NewMallConsts.CURRENT_USER);
        if (user == null) {
            log.info("user==null");
            return false;
        }
        return true;
    }
}
