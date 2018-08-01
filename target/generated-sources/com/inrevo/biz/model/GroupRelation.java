/* 
 * @(#)GroupRelation.java    Created on 2018-06-25
 * Copyright (c) 2018 Inrevo. All rights reserved.
 */
package com.inrevo.biz.model;

import java.util.Date;

import com.inrevo.fiweb.utils.StringIdEntity;

/**
 * 表 ala_group_relation 对应的实体类。
 * 
 * @author (Inrevo Code Generator)
 */
public class GroupRelation extends StringIdEntity {

    private static final long serialVersionUID = 1L;

    private Integer factoryId;
    private Integer userId;
    private Integer alaGroupId;
    private Date createTime;
    public GroupRelation(){}
	
	
	
    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }
	
	
	
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
	
	
	
    public Integer getAlaGroupId() {
        return alaGroupId;
    }

    public void setAlaGroupId(Integer alaGroupId) {
        this.alaGroupId = alaGroupId;
    }
	
	
	
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
