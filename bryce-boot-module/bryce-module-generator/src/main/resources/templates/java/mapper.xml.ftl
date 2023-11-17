<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.${moduleName}.mapper.${entityName}Mapper">

    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${packageName}.${moduleName}.entity.${entityName}">
        <#list fieldList as field>
        <#if field.primaryKey>
        <id column="${field.fieldName}" property="${field.attrName}" />
        <#else>
        <result column="${field.fieldName}" property="${field.attrName}" />
        </#if>
        </#list>
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        <#list fieldList?chunk(5) as row>
        <#list row as field>${field.fieldName}<#sep>, </#sep></#list><#sep>, </#sep>
        </#list>
    </sql>

</mapper>
