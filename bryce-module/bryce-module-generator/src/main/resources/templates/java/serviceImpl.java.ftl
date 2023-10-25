package ${packageName}.${moduleName}.service.impl;

import com.brycehan.boot.common.util.DateTimeUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ${packageName}.common.base.entity.PageResult;
import ${packageName}.framework.mybatis.service.impl.BaseServiceImpl;
import ${packageName}.common.util.ExcelUtils;
import ${packageName}.${moduleName}.convert.${convertName};
import ${packageName}.${moduleName}.dto.${entityPageDtoName};
import ${packageName}.${moduleName}.entity.${entityName};
import ${packageName}.${moduleName}.vo.${entityName}Vo;
import ${packageName}.${moduleName}.service.${serviceName};
import ${packageName}.${moduleName}.mapper.${mapperName};
<#if queryList?filter(f -> f.attrType == "String")?size gt 0>
import org.apache.commons.lang3.StringUtils;
</#if>
<#if queryList?filter(f -> f.attrType != "String")?size gt 0>
import java.util.Objects;
</#if>
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * ${tableComment}服务实现
 *
 * @author ${author}
 * @since ${date}
 */
@Service
@RequiredArgsConstructor
public class ${serviceImplName} extends BaseServiceImpl<${mapperName}, ${entityName}> implements ${serviceName} {

    @Override
    public PageResult<${entityName}Vo> page(${entityPageDtoName} ${entityParam}PageDto) {

        IPage<${entityName}> page = this.baseMapper.selectPage(getPage(${entityParam}PageDto), getWrapper(${entityParam}PageDto));

        return new PageResult<>(page.getTotal(), ${convertName}.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param ${entityParam}PageDto ${tableComment}分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<${entityName}> getWrapper(${entityPageDtoName} ${entityParam}PageDto){
        LambdaQueryWrapper<${entityName}> wrapper = new LambdaQueryWrapper<>();
        <#list queryList?sort_by("queryType") as field>
          <#if field.queryType == '=' && field.attrType == 'String'>
        wrapper.eq(StringUtils.isNotEmpty(${entityParam}PageDto.get${field.attrName?cap_first}()), ${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}());
          <#elseif field.queryType == '=' && field.attrType != 'String'>
        wrapper.eq(Objects.nonNull(${entityParam}PageDto.get${field.attrName?cap_first}()), ${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}());
          <#elseif field.queryType == '!='>
        wrapper.ne(StringUtils.isNotEmpty(${entityParam}PageDto.get${field.attrName?cap_first}()), ${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}());
          <#elseif field.queryType == '>'>
        wrapper.gt(StringUtils.isNotEmpty(${entityParam}PageDto.get${field.attrName?cap_first}()), ${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}());
          <#elseif field.queryType == '>='>
        wrapper.ge(StringUtils.isNotEmpty(${entityParam}PageDto.get${field.attrName?cap_first}()), ${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}());
          <#elseif field.queryType == '<'>
        wrapper.lt(StringUtils.isNotEmpty(${entityParam}PageDto.get${field.attrName?cap_first}()), ${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}());
          <#elseif field.queryType == '<='>
        wrapper.le(StringUtils.isNotEmpty(${entityParam}PageDto.get${field.attrName?cap_first}()), ${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}());
          <#elseif field.queryType == 'like'>
        wrapper.like(StringUtils.isNotEmpty(${entityParam}PageDto.get${field.attrName?cap_first}()), ${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}());
          <#elseif field.queryType == 'left like'>
        wrapper.likeLeft(StringUtils.isNotEmpty(${entityParam}PageDto.get${field.attrName?cap_first}()), ${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}());
          <#elseif field.queryType == 'right like'>
        wrapper.likeRight(StringUtils.isNotEmpty(${entityParam}PageDto.get${field.attrName?cap_first}()), ${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}());
          <#elseif field.queryType == 'between'>

        if(${entityParam}PageDto.get${field.attrName?cap_first}Start() != null && ${entityParam}PageDto.get${field.attrName?cap_first}End() != null) {
            wrapper.between(${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}Start(), ${entityParam}PageDto.get${field.attrName?cap_first}End());
        } else if(${entityParam}PageDto.get${field.attrName?cap_first}Start() != null) {
            wrapper.ge(${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}Start());
        }else if(${entityParam}PageDto.get${field.attrName?cap_first}End() != null) {
            wrapper.ge(${entityName}::get${field.attrName?cap_first}, ${entityParam}PageDto.get${field.attrName?cap_first}End());
        }

          </#if>
        </#list>
        return wrapper;
    }

    @Override
    public void export(${entityPageDtoName} ${entityParam}PageDto) {
        List<${entityName}> ${entityParam}List = this.baseMapper.selectList(getWrapper(${entityParam}PageDto));
        List<${entityName}Vo> ${entityParam}VoList = ${convertName}.INSTANCE.convert(${entityParam}List);
        ExcelUtils.export(${entityName}Vo.class, "${tableComment}_".concat(DateTimeUtils.today()), "${tableComment}", ${entityParam}VoList);
    }

}
