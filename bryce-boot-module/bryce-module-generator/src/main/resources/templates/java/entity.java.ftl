package ${packageName}.${moduleName}.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
<#if baseClass??>
import lombok.EqualsAndHashCode;
</#if>
<#list importList as item>
import ${item!};
</#list>
<#if baseClass??>
import ${baseClass.packageName}.${baseClass.code};
<#else>
import java.io.Serializable;
</#if>
import java.io.Serial;

/**
 * ${tableComment}entity
 *
 * @author ${author}
 * @since ${date}
 */
@Data
<#if baseClass??>
@EqualsAndHashCode(callSuper = true)
</#if>
@TableName("${tableName}")
public class ${entityName} <#if baseClass??>extends ${baseClass.code}<#else>implements Serializable</#if> {

    @Serial
    private static final long serialVersionUID = 1L;

<#list fieldList as field>
<#if !field.baseField>
	<#if field.fieldComment!?length gt 0>
    /**
     * ${field.fieldComment}
     */
	</#if>
  <#if field.autoFill == "INSERT">
    @TableField(fill = FieldFill.INSERT)
  </#if>
	<#if field.autoFill == "INSERT_UPDATE">
    @TableField(fill = FieldFill.INSERT_UPDATE)
	</#if>
	<#if field.autoFill == "UPDATE">
    @TableField(fill = FieldFill.UPDATE)
	</#if>
	<#if field.primaryKey>
    @TableId
	</#if>
    private ${field.attrType} ${field.attrName};

</#if>
</#list>
}
