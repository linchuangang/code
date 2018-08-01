/* 
 * @(#)${dao.className}.java    Created on ${creationDate!}
 * Copyright (c) ${currentYear!} ${author}. All rights reserved.
 */
package ${dao.packageName};

import com.${entity.projectPackageName}.common.annotation.MyBatisBasicDao;
import com.${entity.projectPackageName}.common.annotation.MyBatisRepository;

import ${entity.classFullName};

/**
 * 表 ${entity.tableName} 对应的 DAO 接口。
 * 
 * @author (${author} Code Generator)
 */
@MyBatisRepository
public interface ${dao.className} extends MyBatisBasicDao<${entity.className}> {
}
