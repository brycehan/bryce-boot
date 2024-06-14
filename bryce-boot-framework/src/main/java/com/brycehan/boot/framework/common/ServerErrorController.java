package com.brycehan.boot.framework.common;

import com.brycehan.boot.common.base.http.HttpResponseStatus;
import com.brycehan.boot.common.base.http.ResponseResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(path = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseResult<String> handleError(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        log.error("请求出错了，状态码：{}", status);
        if(status == 404) {
            return ResponseResult.error(HttpResponseStatus.HTTP_NOT_FOUND);
        }

        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        return ResponseResult.error(throwable.getMessage());
    }

}
