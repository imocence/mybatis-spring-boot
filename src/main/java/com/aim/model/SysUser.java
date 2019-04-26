package com.aim.model;

import com.common.base.BaseEntity;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2018/10/24
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class SysUser extends BaseEntity<SysUser> {
    private static final long serialVersionUID = 1L;

    /** 用户姓名 (无默认值) */
    private String name;

    /** 登陆用户名(登陆号) (无默认值) */
    private String username;

    /** 用户密码 (无默认值) */
    private String password;

    /** 描述 (无默认值) */
    private String desc;

    /** 是否被禁用 0禁用1正常  (默认值为: 0) */
    private Boolean enabled;

    /** 是否是超级用户 0非1是  (默认值为: 0) */
    private Integer isys;
}