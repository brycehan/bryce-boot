package ${packageName}.${moduleName}.controller;

import ${packageName}.common.base.entity.PageResult;
import ${packageName}.common.base.http.ResponseResult;
import ${packageName}.common.base.dto.IdsDto;
import ${packageName}.common.validator.SaveGroup;
import ${packageName}.common.validator.UpdateGroup;
import ${packageName}.framework.operatelog.annotation.OperateLog;
import ${packageName}.framework.operatelog.annotation.OperateType;
import ${packageName}.${moduleName}.convert.${convertName};
import ${packageName}.${moduleName}.dto.${entityName}Dto;
import ${packageName}.${moduleName}.dto.${entityPageDtoName};
import ${packageName}.${moduleName}.entity.${entityName};
import ${packageName}.${moduleName}.service.${serviceName};
import ${packageName}.${moduleName}.vo.${entityName}Vo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * ${tableComment}API
 *
 * @author ${author}
 * @since ${date}
 */
@Tag(name = "${tableComment}", description = "${entityParam}")
@RequestMapping("/${moduleName}/${functionName}")
@RestController
@RequiredArgsConstructor
public class ${controllerName} {

    private final ${serviceName} ${serviceParam};

    /**
     * 保存${tableComment}
     *
     * @param ${entityParam}Dto ${tableComment}Dto
     * @return 响应结果
     */
    @Operation(summary = "保存${tableComment}")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody ${entityName}Dto ${entityParam}Dto) {
        this.${serviceParam}.save(${entityParam}Dto);
        return ResponseResult.ok();
    }

    /**
     * 更新${tableComment}
     *
     * @param ${entityParam}Dto ${tableComment}Dto
     * @return 响应结果
     */
    @Operation(summary = "更新${tableComment}")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody ${entityName}Dto ${entityParam}Dto) {
        this.${serviceParam}.update(${entityParam}Dto);
        return ResponseResult.ok();
    }

    /**
     * 删除${tableComment}
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除${tableComment}")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.${serviceParam}.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询${tableComment}详情
     *
     * @param id ${tableComment}ID
     * @return 响应结果
     */
    @Operation(summary = "查询${tableComment}详情")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<${entityName}Vo> get(@Parameter(description = "${tableComment}ID", required = true) @PathVariable Long id) {
        ${entityName} ${entityParam} = this.${serviceParam}.getById(id);
        return ResponseResult.ok(${convertName}.INSTANCE.convert(${entityParam}));
    }

    /**
     * 分页查询
     *
     * @param ${entityParam}PageDto 查询条件
     * @return ${tableComment}分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<${entityName}Vo>> page(@Validated @RequestBody ${entityPageDtoName} ${entityParam}PageDto) {
        PageResult<${entityName}Vo> page = this.${serviceParam}.page(${entityParam}PageDto);
        return ResponseResult.ok(page);
    }

    /**
     * ${tableComment}导出数据
     *
     * @param ${entityParam}PageDto 查询条件
     */
    @Operation(summary = "${tableComment}导出")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody ${entityPageDtoName} ${entityParam}PageDto) {
        this.${serviceParam}.export(${entityParam}PageDto);
    }

}