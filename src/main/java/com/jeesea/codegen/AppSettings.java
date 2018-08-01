package com.jeesea.codegen;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置参数类。
 *
 * @author Rocky
 */
@Component
public class AppSettings {

    @Value("#{settings['codegen.path']}")
    private String codegenPath;
    @Value("#{settings['entity.tables']}")
    private String entityTables;
    @Value("#{settings['entity.package']}")
    private String entityPackageName;
    @Value("#{settings['dao.package']}")
    private String daoPackageName;
    @Value("#{settings['table.prefix.ignore']}")
    private String tableIgnorePrefix;

    @Value("#{settings['service.package']}")
    private String servicePackageName;

    @Value("#{settings['serviceImpl.package']}")
    private String serviceImplPackageName;

    @Value("#{settings['project.package']}")
    private String projectPackageName;

    public String getProjectPackageName() {
        return projectPackageName;
    }

    public void setProjectPackageName(String projectPackageName) {
        this.projectPackageName = projectPackageName;
    }

    public String getServiceImplPackageName() {
        return serviceImplPackageName;
    }

    public void setServiceImplPackageName(String serviceImplPackageName) {
        this.serviceImplPackageName = serviceImplPackageName;
    }

    public String getCodegenPath() {
        return codegenPath;
    }

    public void setCodegenPath(String codegenPath) {
        this.codegenPath = codegenPath;
    }

    public String getEntityTables() {
        return entityTables;
    }

    public Set<String> getEntityTableSet() {
        Set<String> tableSet = new HashSet<>();
        String[] tables = entityTables.split(",");
        for (String table : tables) {
            if (!StringUtils.isBlank(table)) {
                tableSet.add(table.trim());
            }
        }
        return tableSet;
    }

    public void setEntityTables(String entityTables) {
        this.entityTables = entityTables;
    }

    public String getEntityPackageName() {
        return entityPackageName;
    }

    public void setEntityPackageName(String entityPackageName) {
        this.entityPackageName = entityPackageName;
    }

    public String getDaoPackageName() {
        return daoPackageName;
    }

    public void setDaoPackageName(String daoPackageName) {
        this.daoPackageName = daoPackageName;
    }

    public String getTableIgnorePrefix() {
        return tableIgnorePrefix;
    }

    public void setTableIgnorePrefix(String tableIgnorePrefix) {
        this.tableIgnorePrefix = tableIgnorePrefix;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

}
