package com.jeesea.codegen.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 实体类的属性模型。
 *
 * @author Rocky
 */
public class AttributeModel {

    private String attrName; // 属性名称，例如：id
    private String attrType; // 属性类型，例如：String
    private String columnName; // 数据库列名，例如：user_id
    private String columnType; // 数据库列类型，例如：int
    private int columnSize; // 数据库列的大小，例如：10
    private boolean pkColumn; // 是否是主键列
    private boolean pkAutoIncrement; // 主键是否是自增列
    private boolean updatedColumn; // 是否是 modify_time 列
    private boolean createdColumn; // 是否是 creation_time 列

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public boolean isPkColumn() {
        return pkColumn;
    }

    public void setPkColumn(boolean pkColumn) {
        this.pkColumn = pkColumn;
    }

    public boolean isPkAutoIncrement() {
        return pkAutoIncrement;
    }

    public void setPkAutoIncrement(boolean pkAutoIncrement) {
        this.pkAutoIncrement = pkAutoIncrement;
    }

    public boolean isUpdatedColumn() {
        return updatedColumn;
    }

    public void setUpdatedColumn(boolean updatedColumn) {
        this.updatedColumn = updatedColumn;
    }

    public boolean isCreatedColumn() {
        return createdColumn;
    }

    public void setCreatedColumn(boolean createdColumn) {
        this.createdColumn = createdColumn;
    }

    public String getAttrValueForInsert() {
        return isCreatedColumn() ? "now()" : "#{" + attrName + "}";
    }

    public String getAttrValueForUpdate() {
        return "#{" + attrName + "}";
    }

    /**
     * 是否需要在 entity 中定义。
     */
    public boolean isNeedDefineInEntity() {
        return !(isPkColumn() || isCreatedColumn() || isUpdatedColumn());
    }

    /**
     * 判断是否是通用的 Date 属性，即 modifyTime 和 creationTime。
     */
    public boolean isCommonDateAttr() {
        return isCreatedColumn() || isUpdatedColumn();
    }

    /**
     * 判断是否是额外的 Date 属性，即除了 modifyTime 和 creationTime 之外的。
     */
    public boolean isExtraDateAttr() {
        return !isCommonDateAttr() && "Date".equals(attrType);
    }

    /**
     * 判断是否是 Long 类型的 ID 属性。
     */
    public boolean isLongId() {
        return isPkColumn() && "Long".equals(attrType);
    }

    /**
     * 判断是否是 Boolean 类型的字段。
     */
    public boolean isBooleanAttr() {
        return "Boolean".equals(attrType);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
