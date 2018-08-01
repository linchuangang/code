<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${dao.classFullName}">

    <!-- 一般不需要使用这个配置，如果返回数据包含其他表数据，则可以根据这个配置进行修改
    <resultMap id="${entity.className}Result" type="${entity.classFullName}">
    <#list entity.attrs as attr>
        <result property="${attr.attrName}" column="${attr.columnName}"<#if attr.longId> javaType="long"</#if> />
    </#list>
    </resultMap>
    -->

    <sql id="findColumn">
        select
        <#list entity.attrs as attr>
            ${attr.columnName} as ${attr.attrName}<#if attr_has_next>,</#if>
        </#list>
        from ${entity.tableName}
    </sql>

    <sql id="findCondition">
        <where>
        <#list entity.attrs as attr>
          <#if !(attr.createdColumn || attr.updatedColumn)>
            <if test="${attr.attrName} != null">
                and ${attr.columnName} = #${r"{"}${attr.attrName}}
            </if>
          </#if>
        </#list>
        </where>
    </sql>

    <sql id="findConditionWithPage">
        <where>
        <#list entity.attrs as attr>
          <#if !(attr.createdColumn || attr.updatedColumn)>
            <if test="param1.${attr.attrName} != null">
                and ${attr.columnName} = #${r"{"}param1.${attr.attrName}}
            </if>
          </#if>
        </#list>
        </where>
    </sql>

    <sql id="findByIds">
        <include refid="findColumn" />
        where ${entity.idAttr.columnName} in
        <foreach item="${entity.idAttr.attrName}" collection="array" open="(" separator="," close=")">
            ${entity.idAttr.attrValueForUpdate}
        </foreach>
    </sql>

    <select id="find" parameterType="${entity.idAttr.attrType?lower_case}" resultType="${entity.className}">
        <include refid="findColumn" />
        where ${entity.idAttr.columnName} = ${entity.idAttr.attrValueForUpdate}
    </select>

    <select id="findByIds" resultType="${entity.className}">
        <include refid="findByIds" />
    </select>

    <select id="findMap" resultType="${entity.className}">
        <include refid="findByIds" />
    </select>

    <select id="findByEntity" parameterType="${entity.className}" resultType="${entity.className}">
        <include refid="findColumn" />
        <include refid="findCondition" />
    </select>

    <select id="findByEntityWithPage" parameterType="map" resultType="${entity.className}">
        <include refid="findColumn" />
        <include refid="findConditionWithPage" />
    </select>

    <select id="findByParam" parameterType="map" resultType="${entity.className}">
        <include refid="findColumn" />
        <!-- 这里故意返回空列表，使用此方法前请先根据实际业务修改下面的条件 -->
        where 1 = 0;
    </select>

    <select id="findByParamWithPage" parameterType="map" resultType="${entity.className}">
        <include refid="findColumn" />
        <!-- 这里故意返回空列表，使用此方法前请先根据实际业务修改下面的条件 -->
        where 1 = 0;
    </select>

    <insert id="insert" parameterType="${entity.className}"<#if entity.idAttr.pkAutoIncrement> useGeneratedKeys="true" keyProperty="${entity.idAttr.attrName}"</#if>>
        insert into ${entity.tableName}(
        <#list entity.attrsForInsert as attr>
            ${attr.columnName}<#if attr_has_next>,<#else>)</#if>
        </#list>
        values(
        <#list entity.attrsForInsert as attr>
            ${attr.attrValueForInsert}<#if attr_has_next>,<#else>)</#if>
        </#list>
    </insert>

    <update id="update" parameterType="${entity.className}">
        update ${entity.tableName}
        set
        <#list entity.attrsForUpdate as attr>
            ${attr.columnName} = ${attr.attrValueForUpdate}<#if attr_has_next>,</#if>
        </#list>
        where ${entity.idAttr.columnName} = ${entity.idAttr.attrValueForUpdate}
    </update>

    <update id="updateIfPossible" parameterType="${entity.className}">
        update ${entity.tableName}
        <set>
        <#list entity.attrsForUpdate as attr>
            <if test="${attr.attrName} != null">${attr.columnName} = ${attr.attrValueForUpdate}<#if attr_has_next>,</#if></if>
        </#list>
        </set>
        where ${entity.idAttr.columnName} = ${entity.idAttr.attrValueForUpdate}
    </update>

    <delete id="delete">
        delete from ${entity.tableName} where ${entity.idAttr.columnName} in
        <foreach item="${entity.idAttr.attrName}" collection="array" open="(" separator="," close=")">
            ${entity.idAttr.attrValueForUpdate}
        </foreach>
    </delete>

</mapper>
