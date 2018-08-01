/* 
 * @(#)GroupRelationService.java    Created on 2018-06-25
 * Copyright (c) 2018 Inrevo. All rights reserved.
 */
package com.inrevo.biz.service;

import java.util.List;

import com.inrevo.fiweb.common.mybatis.Pagination;

import com.inrevo.biz.model.GroupRelation;

/**
 * 表 ala_group_relation 对应的 Servcie 接口。
 * 
 * @author (Inrevo Code Generator)
 */
public interface GroupRelationService{
	/**
	* 增加 GroupRelation
 	*/
	void addGroupRelation(GroupRelation groupRelation);
	
	/**
	* 获取 List<GroupRelation>,分页
 	*/
	List<GroupRelation> listWithPage(GroupRelation groupRelation, Pagination page);
	
	/**
	* 通过ID查找 GroupRelation
 	*/
	GroupRelation getGroupRelationById(Integer id);
	
	/**
	* 通过ID删除对象 GroupRelation
 	*/
	void removeGroupRelationById(Integer id);
	
	/**
	* 通过对象查找 List<GroupRelation>
 	*/
	List<GroupRelation> listByEntity(GroupRelation groupRelation);
	
	/**
	* 更新 GroupRelation
 	*/
	void modifyGroupRelation(GroupRelation groupRelation);
}
