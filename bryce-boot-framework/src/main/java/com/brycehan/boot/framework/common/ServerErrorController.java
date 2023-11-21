package com.brycehan.boot.framework.common;

import com.brycehan.boot.common.base.http.HttpResponseStatus;
import com.brycehan.boot.common.base.http.ResponseResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 错误控制器
 *
 * @author Bryce Han
 * @since 2023/11/21
 */
@Slf4j
@Tag(name = "错误控制器")
@RestController
public class ServerErrorController implements ErrorController {

    public static final String ERROR_PATH = "/error";

    @RequestMapping(path = ERROR_PATH)
    public ResponseResult<String> handleError(HttpServletResponse response) {
        int status = response.getStatus();
        log.error("请求出错了，状态码：{}", status);
        if(status == 404) {
            return ResponseResult.error(HttpResponseStatus.HTTP_NOT_FOUND);
        }

        return ResponseResult.error(HttpResponseStatus.HTTP_INTERNAL_ERROR);
    }
}
