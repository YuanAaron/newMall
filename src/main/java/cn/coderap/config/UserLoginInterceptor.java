package cn.coderap.config;

import cn.coderap.consts.NewMallConsts;
import cn.coderap.exception.UserLoginException;
import cn.coderap.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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
            //目前存在的问题是拦截后没有返回任何内容，因为该方法的返回值是boolean类型，限制住了
            //解决方案一（不推荐）：功能可以实现，但是需要json序列化等，不够优雅，因为要返回的内容已经决定用ResponseVO封装了
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("text/json");
//            response.getWriter().write("error"); //该方法只能返回字符，如果想返回json，只需要先json序列化再返回即可
//            return false;
            //解决方案二：抛出自定义异常，被统一异常处理拦截，然后再返回即可
            throw new UserLoginException();
        }
        return true;
    }
}
