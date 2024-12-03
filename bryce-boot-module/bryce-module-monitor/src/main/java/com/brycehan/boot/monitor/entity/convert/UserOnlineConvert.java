package com.brycehan.boot.monitor.entity.convert;

import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.monitor.entity.vo.UserOnlineVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户转换器
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserOnlineConvert {

    UserOnlineConvert INSTANCE = Mappers.getMapper(UserOnlineConvert.class);

    UserOnlineVo convert(LoginUser loginUser);

}