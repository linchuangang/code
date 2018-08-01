/* 
 * @(#)GroupService.java    Created on 2018-06-25
 * Copyright (c) 2018 Inrevo. All rights reserved.
 */
package com.inrevo.biz.service;

import java.util.List;

import com.inrevo.fiweb.common.mybatis.Pagination;

import com.inrevo.biz.model.Group;

/**
 * 表 ala_group 对应的 Servcie 接口。
 * 
 * @author (Inrevo Code Generator)
 */
public interface GroupService{
	/**
	* 增加 Group
 	*/
	void addGroup(Group group);
	
	/**
	* 获取 List<Group>,分页
 	*/
	List<Group> listWithPage(Group group, Pagination page);
	
	/**
	* 通过ID查找 Group
 	*/
	Group getGroupById(Integer id);
	
	/**
	* 通过ID删除对象 Group
 	*/
	void removeGroupById(Integer id);
	
	/**
	* 通过对象查找 List<Group>
 	*/
	List<Group> listByEntity(Group group);
	
	/**
	* 更新 Group
 	*/
	void modifyGroup(Group group);
}
