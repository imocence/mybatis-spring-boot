package com.security.core;

import com.common.bean.ResultJson;
import com.common.utils.RequestUtil;
import com.security.utils.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @AUTO 认证切入点，这里使用它的目的是保证当用户登录之前就访问前后台时，会跳转到不同的登录页面
 * @Author AIM
 * @DATE 2018/5/21
 */
public class DefaultLoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(DefaultLoginUrlEntryPoint.class);

    //默认登陆后跳转的首页地址
    private String defaultIndexUrl = "/login";

    public DefaultLoginUrlEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
        logger.info("===========================================> 访问文件名为:DefaultLoginUrlEntryPoint，方法名为:" + loginFormUrl);
        this.defaultIndexUrl = loginFormUrl;
    }

    //当访问的资源没有权限，会调用这里
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 判断是不是ajax请求
        boolean isAjax = RequestUtil.isAjax(request);
        //表示当前用户是否已经登录认证成功了
        boolean hasSession = SecurityHelper.isAuthenticated();
        if (isAjax && !hasSession) {
            this.transformAjaxRequest(request, response);
        } else {
            String targetUrl = this.defaultIndexUrl;
            String url = request.getRequestURI();
            if (url.indexOf("admin") != -1) {
                //未登录而访问后台受控资源时，跳转到后台登录页面
                targetUrl = "/admin/login";
            }

            targetUrl = request.getContextPath() + targetUrl;
            logger.info("=======================================================================> 当前的访问路径：" + targetUrl);
            response.sendRedirect(targetUrl);
        }
    }

    /**
     * 转换Ajax请求
     */
    private void transformAjaxRequest(HttpServletRequest request, HttpServletResponse response) {
        RequestUtil.writeToBrowser((new ResultJson("Session超时，请重新登录")).toString(), response);
    }
}
