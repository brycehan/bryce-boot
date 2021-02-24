package com.brycehan.boot.system.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brycehan.boot.core.ResultWrapper;
import com.brycehan.boot.core.vo.PageVO;
import com.brycehan.boot.system.entity.User;
import com.brycehan.boot.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Bryce Han
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResultWrapper save(User user){
        Boolean saved = userService.save(user);
        return ResultWrapper.builder()
                .result(saved)
                .build();
    }

    @PutMapping
    public ResultWrapper update(User user){
        Boolean updated = userService.updateById(user);
        return ResultWrapper.builder()
                .result(updated)
                .build();
    }

    @DeleteMapping
    public ResultWrapper delete(User user){
        Boolean deleted = false;
        if(user.getId() != null){
            deleted = userService.removeById(user.getId());
        }

        return ResultWrapper.builder()
                .result(deleted)
                .build();
    }

    @DeleteMapping
    public ResultWrapper delete(List<Serializable> idList){
        Boolean deleted = false;
        if(!idList.isEmpty()){
            deleted = userService.removeByIds(idList);
        }

        return ResultWrapper.builder()
                .result(deleted)
                .build();
    }

    @GetMapping(value = "page")
    public ResultWrapper page(@Valid PageVO pageVO, User user){
        Page page = new Page(pageVO.getCurrent(), pageVO.getSize());

        page = userService.page(page, Wrappers.lambdaQuery(user));
        return ResultWrapper.builder()
                .result(page)
                .build();
    }
}
