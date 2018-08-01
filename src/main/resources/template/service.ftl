/* 
 * @(#)${service.className}.java    Created on ${creationDate!}
 * Copyright (c) ${currentYear!} ${author}. All rights reserved.
 */
package ${service.packageName};

import java.util.List;

import com.${entity.projectPackageName}.common.mybatis.Pagination;

import ${entity.classFullName};

/**
 * 表 ${entity.tableName} 对应的 Servcie 接口。
 * 
 * @author (${author} Code Generator)
 */
public interface ${service.className}{
	/**
	* 增加 ${service.entityName}
 	*/
	void add${service.entityName}(${service.entityName} ${service.variableName});
	
	/**
	* 获取 List<${service.entityName}>,分页
 	*/
	List<${service.entityName}> listWithPage(${service.entityName} ${service.variableName}, Pagination page);
	
	/**
	* 通过ID查找 ${service.entityName}
 	*/
	${service.entityName} get${service.entityName}ById(Integer id);
	
	/**
	* 通过ID删除对象 ${service.entityName}
 	*/
	void remove${service.entityName}ById(Integer id);
	
	/**
	* 通过对象查找 List<${service.entityName}>
 	*/
	List<${service.entityName}> listByEntity(${service.entityName} ${service.variableName});
	
	/**
	* 更新 ${service.entityName}
 	*/
	void modify${service.entityName}(${service.entityName} ${service.variableName});
}
