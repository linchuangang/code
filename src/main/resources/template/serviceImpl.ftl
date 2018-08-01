/* 
 * @(#)${serviceImpl.className}.java    Created on ${creationDate!}
 * Copyright (c) ${currentYear!} ${author}. All rights reserved.
 */
package ${serviceImpl.packageName};

import java.util.List;

import com.${entity.projectPackageName}.common.mybatis.Pagination;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.${entity.projectPackageName}.common.mybatis.Pagination;
import com.${entity.projectPackageName}.dao.${serviceImpl.entityName}Dao;
import com.${entity.projectPackageName}.service.${serviceImpl.entityName}Service;

import ${entity.classFullName};

/**
 * 表 ${entity.tableName} 对应的 Servcie 接口实现。
 * 
 * @author (${author} Code Generator)
 */
@Service
public class ${serviceImpl.className} implements ${serviceImpl.entityName}Service{
	@Resource
    private ${serviceImpl.entityName}Dao ${serviceImpl.variableName}Dao;
    
	@Override
	public void add${serviceImpl.entityName}(${serviceImpl.entityName} ${serviceImpl.variableName}){
		${serviceImpl.variableName}Dao.insert(${serviceImpl.variableName});
	}
	
	@Override
	public List<${serviceImpl.entityName}> listWithPage(${serviceImpl.entityName} ${serviceImpl.variableName}, Pagination page){
		return ${serviceImpl.variableName}Dao.findByEntityWithPage(${serviceImpl.variableName}, page);
	}
	
	@Override
	public ${serviceImpl.entityName} get${serviceImpl.entityName}ById(Integer id){
		return ${serviceImpl.variableName}Dao.find(id);
	}
	
	@Override
	public void remove${serviceImpl.entityName}ById(Integer id){
		${serviceImpl.variableName}Dao.delete(id);
	}
	
	@Override
	public List<${serviceImpl.entityName}> listByEntity(${serviceImpl.entityName} ${serviceImpl.variableName}){
		return ${serviceImpl.variableName}Dao.findByEntity(${serviceImpl.variableName});
	}
	
	@Override
	public void modify${serviceImpl.entityName}(${serviceImpl.entityName} ${serviceImpl.variableName}){
		${serviceImpl.variableName}Dao.updateIfPossible(${serviceImpl.variableName});
	}
}
