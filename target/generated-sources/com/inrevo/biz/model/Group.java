/* 
 * @(#)Group.java    Created on 2018-06-25
 * Copyright (c) 2018 Inrevo. All rights reserved.
 */
package com.inrevo.biz.model;

import java.util.Date;

import com.inrevo.fiweb.utils.StringIdEntity;

/**
 * 表 ala_group 对应的实体类。
 * 
 * @author (Inrevo Code Generator)
 */
public class Group extends StringIdEntity {

    private static final long serialVersionUID = 1L;

    private Integer factoryId;
    private Integer state;
    private String name;
    private String remark;
    private Date createTime;
    public Group(){}
	
	
	
    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }
	
	
	
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
	
	
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
	
	
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
	
	
	
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
