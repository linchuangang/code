package com.jeesea.codegen.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 类模型基类。
 *
 * @author Rocky
 */
public abstract class BaseModel {

    private String className; // 短类名，例如：User
    private String packageName; // 类所在的包名，例如：foo.bar
    private String projectPackageName;

    public String getProjectPackageName() {
        return projectPackageName;
    }

    public void setProjectPackageName(String projectPackageName) {
        this.projectPackageName = projectPackageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassFullName() {
        return packageName + "." + className;
    }

    /**
     * 根据 Java 类全名获取类的文件目录。例如：foo.bar.Test => foo/bar/Test
     */
    public String getClassFilePath() {
        return StringUtils.replaceChars(getClassFullName(), '.', '/');
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
