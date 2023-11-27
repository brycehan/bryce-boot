package ${packageName}.${moduleName}.vo;

import cn.hutool.core.date.DatePattern;
import io.swagger.v3.oas.annotations.media.Schema;
<#if fieldList?filter(f -> f.formItem || f.queryItem || f.gridItem)?filter(f -> f.attrType == 'LocalDateTime')?size gt 0>
import com.fasterxml.jackson.annotation.JsonFormat;
</#if>
import lombok.Data;
<#list voImportList as item>
import ${item!};
</#list>

import java.io.Serializable;
import java.io.Serial;

/**
 * ${tableComment}Vo
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@Schema(description = "${tableComment}Vo")
public class ${entityName}Vo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

<#list fieldList?filter(f -> f.primaryKey || f.formItem || f.queryItem || f.gridItem) as field>
	<#if field.fieldComment!?length gt 0>
    /**
     * ${field.fieldComment}
     */
	</#if>
    @Schema(description = "${field.fieldComment}")
  <#if field.attrType == 'LocalDateTime'>
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
  </#if>
    private ${field.attrType} ${field.attrName};

</#list>
}
