package com.brycehan.boot.framework.common;

import com.brycehan.boot.common.base.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页欢迎信息
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Tag(name = "首页")
@RestController
public class IndexController {

    /**
     * 欢迎信息
     *
     * @return 响应结果
     */
    @Operation(summary = "欢迎信息")
    @GetMapping(path = "/")
    public ResponseResult<String> index() {
        return ResponseResult.ok("您好，项目已启动，请通过前端地址访问。");
    }

}
