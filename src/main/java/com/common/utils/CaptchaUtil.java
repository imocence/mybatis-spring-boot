package com.common.utils;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.Captcha.Builder;
import cn.apiclub.captcha.servlet.CaptchaServletUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @AUTO 验证码工具
 * @Author AIM
 * @DATE 2018/6/15
 */
public final class CaptchaUtil {
    private static final int WIDTH = 160;
    private static final int HEIGHT = 40;

    private CaptchaUtil() {
        throw new Error("工具类不能实例化！");
    }

    public static void getCaptcha(HttpSession session, HttpServletResponse response) {
        getCaptcha(session, response, 160, 40);
    }

    public static void getCaptcha(HttpSession session, HttpServletResponse response, int width, int height) {
        Captcha localCaptcha = (new Builder(width, height)).addText().addNoise().build();
        CaptchaServletUtil.writeImage(response, localCaptcha.getImage());
        session.setAttribute("simpleCaptcha", localCaptcha);
    }

    public static void resetCaptcha(HttpSession session) {
        session.setAttribute("simpleCaptcha", (Object) null);
    }

    public static boolean checkCaptcha(HttpSession session, String checkCode) {
        Captcha localCaptcha = (Captcha) session.getAttribute("simpleCaptcha");
        return localCaptcha == null ? false : localCaptcha.isCorrect(checkCode);
    }
}
