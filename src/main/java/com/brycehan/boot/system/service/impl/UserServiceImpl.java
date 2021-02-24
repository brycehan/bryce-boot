package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.system.entity.User;
import com.brycehan.boot.system.mapper.UserMapper;
import com.brycehan.boot.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Bryce Han
 * @since 2021-02-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
