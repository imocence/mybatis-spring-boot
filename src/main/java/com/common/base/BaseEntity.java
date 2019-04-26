package com.common.base;

import java.io.Serializable;
import java.util.Date;

/**
 * @AUTO Bean基类
 * @FILE BaseEntity.java
 * @DATE 2018-3-23 下午2:40:09
 * @Author AIM
 */
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体编号（唯一标识）
     */
    protected Long id;

    protected Date creatdate;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(Date creatdate) {
        this.creatdate = creatdate;
    }
}
