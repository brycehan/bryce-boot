package ${packageName}.${moduleName}.mapper;

import ${packageName}.common.base.mapper.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ${packageName}.${moduleName}.entity.${entityName};

/**
* ${tableComment}Mapper接口
*
* @author ${author}
* @since ${date}
*/
@Mapper
public interface ${mapperName} extends BryceBaseMapper<${entityName}> {

}
