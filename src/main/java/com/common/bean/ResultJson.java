package com.common.bean;

import com.common.utils.ConvertJson;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * @AUTO 返回信息实体
 * @Author AIM
 * @DATE 2018/6/15
 */
public class ResultJson implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final boolean SUCCESS = true;
    public static final boolean FAULT = false;
    // 请求是否成功
    private boolean success;
    // 返回信息
    private String msg;
    private String exceptionDetailMsg;
    private Object obj;

    public ResultJson() {
        this.success = true;
    }

    /**
     * 请求成功返回信息
     *
     * @param message
     */
    public ResultJson(String message) {
        this.success = SUCCESS;
        this.msg = message;
    }

    /**
     * 请求成功返回Object
     *
     * @param object 信息对象
     */
    public ResultJson(Object object) {
        this.success = SUCCESS;
        this.obj = object;
    }

    /**
     * 自定义返回请求状态
     *
     * @param suc 状态
     */
    public ResultJson(boolean suc) {
        this.success = suc;
    }

    /**
     * 返回自定义信息内容
     *
     * @param suc     状态
     * @param message 消息
     */
    public ResultJson(boolean suc, String message) {
        this.success = suc;
        this.msg = message;
    }

    /**
     * 返回异常错误信息
     *
     * @param exceptionMessage 异常消息对象
     */
    public ResultJson(Throwable exceptionMessage) {
        exceptionMessage.printStackTrace(new PrintWriter(new StringWriter()));
        this.success = FAULT;
        this.msg = exceptionMessage.getMessage();
    }

    /**
     * 返回异常错误信息
     *
     * @param exceptionMessage 异常消息对象
     * @param detailMsg        是否设置详细信息
     */
    public ResultJson(Throwable exceptionMessage, boolean detailMsg) {
        exceptionMessage.printStackTrace(new PrintWriter(new StringWriter()));
        this.success = FAULT;
        this.msg = exceptionMessage.getMessage();
        if (detailMsg) {
            this.exceptionDetailMsg = exceptionMessage.toString();
        }

    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMsg() {
        return this.msg;
    }

    public Object getObj() {
        return this.obj;
    }

    public String getExceptionDetailMsg() {
        return this.exceptionDetailMsg;
    }

    public String toString() {
        return ConvertJson.toJson(this);
    }
}