package com.wids.controller;

import com.wids.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * 账户管理控制器
 * 使用@Controller注解修饰的类是一个控制器
 * 控制器只做三件事情：
 * 1 取 ：取前端的参数
 * 2 调： 调用业务逻辑层的方法
 * 3 转： 将处理结果转发给前端页面
 * 所以控制器唯一职责就是处理前端的请求
 */
@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    /***
     * 该方法专门用来处理前端页面/login路径的请求，处理请求方式为Post
     * 步骤：
     * 1 调用业务逻辑层的login方法
     * 2 如果方法返回true：跳转到主页面，登录成功
     * 3 如果方法返回false：停留在登录页面，将登录失败信息返回给前端页面
     *
     * @param userName  前端输入的用户名名
     * @param userPwd  前端输入的密码
     * @param model 在前端和后台传输数据的参数
     * @return 登录成功返回主页，失败停留在当前登录页面
     */
    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public String doLogin(String userName , String userPwd, Model model, HttpSession session) {
        boolean result = accountService.login(userName, userPwd);
        if (result) {
            // 登录成功，将当前用户名使用Session作用域对象渲染到前端页面
            session.setAttribute("currentUser",userName);
            // return "waterMenuMenu"; 返回送水工管理系统主页面
            // 页面通常放在 resource/template/woter路径下面，我们在yml文件里面配置了前缀，所以不用写这个
            // 除此之外我们还配置了后缀.html 所以也不用写
            return "waterMainMenu";
//            return "waterMainMenu";
        } else {
            model.addAttribute("loginFail","用户名密码错误");
            return "index";
        }
    }
}
