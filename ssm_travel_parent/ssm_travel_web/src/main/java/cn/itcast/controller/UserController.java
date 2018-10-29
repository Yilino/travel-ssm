package cn.itcast.controller;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/regist")
    public Map regist(User user, String check, HttpSession session){
        Map map = new HashMap<String,Object>();
        //先验证图片验证码是否正确
        String codeFromSession = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (StringUtils.isEmpty(check)||StringUtils.isEmpty(codeFromSession)||!check.equalsIgnoreCase(codeFromSession)){
            map.put("flag",false);
            map.put("errorMsg","验证码输入错误,请重新输入!");
            return map;
        }
        boolean result = userService.register(user);
        if (!result){
            map.put("flag",false);
            map.put("errorMsg","用户名已存在,请重新输入!");
        }else {
            map.put("flag",true);

        }
        return map;
    }
    @RequestMapping("/login")
    public Map login(String username, String password,HttpSession session,String check){
        Map map = new HashMap<String,Object>();
        //先验证图片验证码是否正确
        String codeFromSession = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (StringUtils.isEmpty(check)||StringUtils.isEmpty(codeFromSession)||!check.equalsIgnoreCase(codeFromSession)){
            map.put("flag",false);
            map.put("errorMsg","验证码输入错误,请重新输入!");
            return map;
        }
        User user = userService.login(username, password);
        if (user==null){
            map.put("flag",false);
            map.put("errorMsg","用户名或密码错误,请重新输入!");
        }else {
            session.setAttribute("user",user);
            map.put("flag",true);

        }
        return map;
    }
    @RequestMapping("/findOne")
    public User findOne(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user;
    }
    @RequestMapping("/exit")
    public void exit(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        session.invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

}
