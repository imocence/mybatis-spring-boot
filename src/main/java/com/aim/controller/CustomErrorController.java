package com.aim.controller;

import com.common.base.BaseController;
import com.common.base.PatternAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @AUTO 实现错误请求方法来达到跳转指定错误页面
 * @Author AIM
 * @DATE 2018/10/23
 */
@Controller
public class CustomErrorController extends BaseController implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new PatternAndView(ERROR_PATH + ERROR_PATH);
        try {
            //获取statusCode:401,404,500
            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            logger.info("请求访问获取statusCode：" + statusCode);
            if (statusCode == 401) {// 未授权：登录失败
                mv.setViewName(ERROR_PATH + "/401");
            } else if (statusCode == 403) {//禁止访问
                mv.setViewName(ERROR_PATH + "/403");
            } else if (statusCode == 404) {//找不到
                mv.setViewName(ERROR_PATH + "/404");
            } else if (statusCode == 500) {//服务器的内部错误
                mv.setViewName(ERROR_PATH + "/500");
            }
        } catch (Exception e) {
        }
        return mv;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
