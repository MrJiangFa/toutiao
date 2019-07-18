package com.springtest.toutiao.controller;

import com.springtest.toutiao.service.UserService;
import com.springtest.toutiao.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

    //注册
    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rememberme", defaultValue = "0") int rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);

            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");//设置cookie的路径全栈有效
                response.addCookie(cookie);
                //让此cookie有五天的有效时间
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                return ToutiaoUtil.getJSONString(0, "注册成功");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册异常");
        }
    }

    //登录
    @RequestMapping(path = {"/login/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                        HttpServletResponse response, HttpSession session) {
        try {
            Map<String, Object> map = userService.login(username, password);
//            String sessionId = session.getId();
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");//设置cookie全路径有效
                response.addCookie(cookie);
                //让此cookie有五天的有效时间
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                ServletContext applicaiton = session.getServletContext();
                Map<String, String> USER_SESSION = (Map<String, String>) applicaiton.getAttribute("USER_SESSION");
                if (USER_SESSION == null) {
                    USER_SESSION = new HashMap<>();
                    applicaiton.setAttribute("USER_SESSION", USER_SESSION);
                }
                if (USER_SESSION.containsKey(username)) {
                    if (USER_SESSION.get(username) == session.getId()) {
                        System.out.println(username + "在同一地点重复登录");
                        return ToutiaoUtil.getJSONString(0, "登录成功");
                    } else {
                        System.out.println(username + "已异地登录");
                        USER_SESSION.remove(username);//异地登录之后删除之前登录记录的用户名，sessionId键值对
                        //由于模板引擎中的session不能实时更新，写个定时的js方法不断去获取session的数据
                        return "";
                    }
                } else {
                    USER_SESSION.put(username, session.getId());
                    return ToutiaoUtil.getJSONString(0, "登录成功");
                }
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "登录异常");
        }
    }

    @RequestMapping(path = {"/logout/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
}
