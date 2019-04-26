package com.security.core;

import com.common.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @AUTO 登录失败后的处理
 * @Author AIM
 * @DATE 2018/6/15
 */
@Component("securityLoginFailureHandler")
public class SecurityLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(SecurityLoginFailureHandler.class);

    public static final Integer AUTHENTICATION_FAILURE_CODE = 401;
    private String failureUrl; // 登录失败默认url
    private String usernameParameter = "username"; // 用户名参数

    public SecurityLoginFailureHandler() {
        super();
    }

    public SecurityLoginFailureHandler(final String defaultFailureUrl) {
        failureUrl = defaultFailureUrl;
        setDefaultFailureUrl(defaultFailureUrl);
    }

    /**
     * 认证失败
     */
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter(usernameParameter); // 用户名
        // 多登录页面处理
        String targetUrl = failureUrl; // 每次恢复成默认的
        setDefaultFailureUrl(targetUrl);
        String url = request.getRequestURI();
        if (url.indexOf("admin") != -1) {
            //未登录而访问后台受控资源时，跳转到后台登录页面
            targetUrl = "/admin/login?type=error";
            setDefaultFailureUrl(targetUrl);
        }
        if (targetUrl == null) {
            logger.debug("没有配置defaultFailureUrl, sending 401 Unauthorized error");
            response.sendError(AUTHENTICATION_FAILURE_CODE, "认证失败: " + exception.getMessage());
        } else {
            saveException(request, exception);
            logger.info("=============================> 访问登陆失败处理程序,跳转路径为:" + targetUrl);
            if (isUseForward()) {
                logger.debug(username + "登录失败，Forwarding 到页面 " + targetUrl);
                request.getRequestDispatcher(targetUrl).forward(request, response);
            } else {
                logger.debug(username + "登录失败，Redirecting 到页面 " + targetUrl);
                getRedirectStrategy().sendRedirect(request, response, targetUrl);
            }
        }
    }

    /**
     * setUsernameParameter
     */
    public void setUsernameParameter(final String usernameParameterParm) {
        if (RequestUtil.isNullOrBlank(usernameParameterParm)) {
            logger.error("Username 参数不能为空");
        } else {
            this.usernameParameter = usernameParameterParm;
        }
    }

    /**
     * setFailureUrl
     */
    public void setFailureUrl(final String failureUrlParm) {
        if (UrlUtils.isValidRedirectUrl(failureUrlParm)) {
            logger.error(failureUrlParm + "' 不是有效的 redirect URL");
        }
        this.failureUrl = failureUrlParm;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("afterPropertiesSet");
    }
}
