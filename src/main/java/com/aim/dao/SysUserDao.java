package com.aim.dao;

import com.aim.model.SysUser;
import com.common.base.BaseCrudDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2018/10/24
 */
@Mapper
public interface SysUserDao extends BaseCrudDao<SysUser> {
}