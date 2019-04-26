package com.security.utils;

import com.common.exception.CaptchaIncorrectException;
import com.common.exception.CaptchaRequiredException;
import com.common.utils.CaptchaUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @AUTO 安全帮助工具类
 * @Author AIM
 * @DATE 2018/6/15
 */
public class SecurityHelper {

    private SecurityHelper() {
        throw new Error("工具类不能实例化！");
    }

    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }

    /**
     * 是否已经登陆
     */
    public static boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) ? SecurityContextHolder.getContext().getAuthentication().isAuthenticated() : false;
    }

    /**
     * 获取请求对象
     *
     * @desc springMVC中，为了方便随时获取当前的request对象
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }

        return request;
    }

    /**
     * 请求对象获取session
     */
    public static HttpSession getSession() {
        HttpSession session = null;
        HttpServletRequest request = getRequest();
        if (request != null) {
            session = request.getSession(true);
        }

        return session;
    }

    /**
     * 检查验证码是否存在
     */
    public static void checkValidateCode(String captchaStr) throws CaptchaRequiredException, CaptchaIncorrectException {
        checkValidateCode(captchaStr, getSession());
    }

    /**
     * 检查验证码是否存在
     */
    private static void checkValidateCode(String captchaStr, HttpSession session) throws CaptchaRequiredException, CaptchaIncorrectException {
        String captchaCode = "";
        if (captchaStr != null) {
            captchaCode = captchaStr.trim().toLowerCase();
        }

        if (captchaCode == null) {
            throw new CaptchaRequiredException("请填写验证码！");
        } else if (!CaptchaUtil.checkCaptcha(session, captchaCode)) {
            throw new CaptchaIncorrectException("验证码不正确！");
        }
    }
}
