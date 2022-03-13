package com.wids.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    /**
     * 请求发起之前进行拦截：前端发起请求到后端之前，要判断是否登录，如果没有登录调整到登录页面，并且给出提示“请先登录”
     *  步骤：
     *  1 获取Session对象
     *  2 获取Session对象的用户名
     *  3 判断用户名是否为空，如果为空，如果没有登录调整到登录页面，并且给出提示“请先登录” 返回false
     *  4 如果用户已经登录就放行，返回true
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器
     * @return false没有登录跳转到登录页面，true已登录，放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        Object currentUser = session.getAttribute("currentUser");
        // 条件成立：表示用户没有登录
        if (null == currentUser) {
            // 向前端渲染数据“请先登录”
            session.setAttribute("loginMsg","请先登录");
            // 重定向到登录页面
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }
        return true;
    }
}
