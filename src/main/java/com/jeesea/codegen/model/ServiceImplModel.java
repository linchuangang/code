package com.jeesea.codegen.model;

/**
 * @author Rocky
 */
public class ServiceImplModel extends BaseModel {
    private String tableName;// 对应的表名
    private String entityName;
    private String variableName;//变量名称

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
