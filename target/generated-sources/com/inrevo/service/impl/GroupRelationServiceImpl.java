/* 
 * @(#)GroupRelationServiceImpl.java    Created on 2018-06-25
 * Copyright (c) 2018 Inrevo. All rights reserved.
 */
package com.inrevo.service.impl;

import java.util.List;

import com.inrevo.fiweb.common.mybatis.Pagination;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inrevo.fiweb.common.mybatis.Pagination;
import com.inrevo.fiweb.dao.GroupRelationDao;
import com.inrevo.fiweb.service.GroupRelationService;

import com.inrevo.biz.model.GroupRelation;

/**
 * 表 ala_group_relation 对应的 Servcie 接口实现。
 * 
 * @author (Inrevo Code Generator)
 */
@Service
public class GroupRelationServiceImpl implements GroupRelationService{
	@Resource
    private GroupRelationDao groupRelationDao;
    
	@Override
	public void addGroupRelation(GroupRelation groupRelation){
		groupRelationDao.insert(groupRelation);
	}
	
	@Override
	public List<GroupRelation> listWithPage(GroupRelation groupRelation, Pagination page){
		return groupRelationDao.findByEntityWithPage(groupRelation, page);
	}
	
	@Override
	public GroupRelation getGroupRelationById(Integer id){
		return groupRelationDao.find(id);
	}
	
	@Override
	public void removeGroupRelationById(Integer id){
		groupRelationDao.delete(id);
	}
	
	@Override
	public List<GroupRelation> listByEntity(GroupRelation groupRelation){
		return groupRelationDao.findByEntity(groupRelation);
	}
	
	@Override
	public void modifyGroupRelation(GroupRelation groupRelation){
		groupRelationDao.updateIfPossible(groupRelation);
	}
}
