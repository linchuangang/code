package com.jeesea.codegen.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体类模型。
 *
 * @author Rocky
 */
public class EntityModel extends BaseModel {

    private String tableName; // 对应的表名，例如：user
    private AttributeModel idAttr; // 类的 ID 属性
    private List<AttributeModel> attrs; // 类的所有属性列表
    private boolean containExtraDateAttr; // 除了 modifyTime、creationTime 之外是否还存在其他日期类型的属性

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public AttributeModel getIdAttr() {
        return idAttr;
    }

    public void setIdAttr(AttributeModel idAttr) {
        this.idAttr = idAttr;
    }

    public List<AttributeModel> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<AttributeModel> attrs) {
        this.attrs = attrs;
    }

    /**
     * 获取 DAO insert 操作需要用到的属性。
     */
    public List<AttributeModel> getAttrsForInsert() {
        List<AttributeModel> results = new ArrayList<>();
        for (AttributeModel attr : attrs) {
            if (attr.isPkAutoIncrement() || attr.isUpdatedColumn()) {
                continue;
            }

            results.add(attr);
        }
        return results;
    }

    /**
     * 获取 DAO update 操作需要用到的属性。
     */
    public List<AttributeModel> getAttrsForUpdate() {
        List<AttributeModel> results = new ArrayList<>();
        for (AttributeModel attr : attrs) {
            if (!attr.isNeedDefineInEntity()) {
                continue;
            }

            results.add(attr);
        }
        return results;
    }

    public boolean isContainExtraDateAttr() {
        return containExtraDateAttr;
    }

    public void setContainExtraDateAttr(boolean containExtraDateAttr) {
        this.containExtraDateAttr = containExtraDateAttr;
    }

}
