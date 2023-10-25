package ${packageName}.${moduleName}.dto;

<#if fieldList?filter(field -> field.characterMaximumLength gt 0)?size  gt 0>
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import jakarta.validation.constraints.Size;
</#if>
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
<#list importList as item>
import ${item!};
</#list>
import java.io.Serializable;
import java.io.Serial;

/**
 * ${tableComment}Dto
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@Schema(description = "${tableComment}Dto")
public class ${entityName}Dto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

<#if baseClass??>
    /**
    * ID
    */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

</#if>
<#list fieldList as field>
<#if !field.baseField>
	<#if field.fieldComment!?length gt 0>
    /**
     * ${field.fieldComment}
     */
	</#if>
    @Schema(description = "${field.fieldComment}")
  <#if field.primaryKey>
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
  </#if>
  <#if field.characterMaximumLength gt 0>
    @Size(max = ${field.characterMaximumLength?c}, groups = {SaveGroup.class, UpdateGroup.class})
  </#if>
    private ${field.attrType} ${field.attrName};

</#if>
</#list>
}
