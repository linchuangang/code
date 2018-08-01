/* 
 * @(#)${entity.className}.java    Created on ${creationDate!}
 * Copyright (c) ${currentYear!} ${author}. All rights reserved.
 */
package ${entity.packageName};
<#if entity.containExtraDateAttr>

import java.util.Date;
</#if>

<#if entity.idAttr.attrType == "Long">
  <#assign idEntity = "LongIdEntity">
<#else>
  <#assign idEntity = "StringIdEntity">
</#if>
import com.${entity.projectPackageName}.utils.${idEntity};

/**
 * 表 ${entity.tableName} 对应的实体类。
 * 
 * @author (${author} Code Generator)
 */
public class ${entity.className} extends ${idEntity} {

    private static final long serialVersionUID = 1L;

    <#list entity.attrs as attr>
      <#if attr.needDefineInEntity>
    private ${attr.attrType} ${attr.attrName};
      </#if>
    </#list>
    public ${entity.className}(){}
    <#list entity.attrs as attr>
      <#if attr.needDefineInEntity>
	
	
	
    public ${attr.attrType} get${attr.attrName?cap_first}() {
        return ${attr.attrName};
    }

    public void set${attr.attrName?cap_first}(${attr.attrType} ${attr.attrName}) {
        this.${attr.attrName} = ${attr.attrName};
    }
        <#if attr.booleanAttr>

    public boolean is${attr.attrName?substring(2)?cap_first}() {
        return ${attr.attrName} != null && ${attr.attrName};
    }
        </#if>
      </#if>
    </#list>

}
