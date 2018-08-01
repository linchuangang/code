/* 
 * @(#)GroupServiceImpl.java    Created on 2018-06-25
 * Copyright (c) 2018 Inrevo. All rights reserved.
 */
package com.inrevo.service.impl;

import java.util.List;

import com.inrevo.fiweb.common.mybatis.Pagination;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inrevo.fiweb.common.mybatis.Pagination;
import com.inrevo.fiweb.dao.GroupDao;
import com.inrevo.fiweb.service.GroupService;

import com.inrevo.biz.model.Group;

/**
 * 表 ala_group 对应的 Servcie 接口实现。
 * 
 * @author (Inrevo Code Generator)
 */
@Service
public class GroupServiceImpl implements GroupService{
	@Resource
    private GroupDao groupDao;
    
	@Override
	public void addGroup(Group group){
		groupDao.insert(group);
	}
	
	@Override
	public List<Group> listWithPage(Group group, Pagination page){
		return groupDao.findByEntityWithPage(group, page);
	}
	
	@Override
	public Group getGroupById(Integer id){
		return groupDao.find(id);
	}
	
	@Override
	public void removeGroupById(Integer id){
		groupDao.delete(id);
	}
	
	@Override
	public List<Group> listByEntity(Group group){
		return groupDao.findByEntity(group);
	}
	
	@Override
	public void modifyGroup(Group group){
		groupDao.updateIfPossible(group);
	}
}
