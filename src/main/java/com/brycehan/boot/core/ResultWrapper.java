package com.brycehan.boot.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Bryce Han
 * @date 2021/2/23
 */
@Data
@NoArgsConstructor
public class ResultWrapper {
    private Integer status;

    private String message;

    private Object result;

    public ResultWrapper(ResultBuilder builder){
        this.status = builder.status;
        this.message = builder.message;
        this.result = builder.result;
    }

    @Data
    @Accessors(fluent = true)
    public static class ResultBuilder{
        private Integer status;

        private String message;

        private Object result;

        public ResultWrapper build(){
            return new ResultWrapper(this);
        }
    }

    public static ResultBuilder builder(){
        return new ResultBuilder().status(200).message("OK");
    }
}
