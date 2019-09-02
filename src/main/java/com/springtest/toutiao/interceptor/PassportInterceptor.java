package com.springtest.toutiao.interceptor;

import com.springtest.toutiao.dao.LoginTicketDao;
import com.springtest.toutiao.dao.UserDao;
import com.springtest.toutiao.model.HostHolder;
import com.springtest.toutiao.model.LoginTicket;
import com.springtest.toutiao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 拦截器
 */

@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketDao loginTicketDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HostHolder hostHolder;

    /**
     * preHandle 在请求发生前执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        //有ticket，但是可能是伪造的，需要进行验证。
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
            //尚未登录，时间过期，登录状态不为0
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }
            User user = userDao.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        System.out.println("执行拦截器方法");
        return true;
    }

    //postHandle 在请求完成后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
