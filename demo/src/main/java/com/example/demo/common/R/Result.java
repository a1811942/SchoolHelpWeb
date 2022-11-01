package com.example.demo.common.R;

import lombok.Data;

import java.util.Map;

@Data
public class Result<T> {

    private T result; //返回的数据
    private StatusCode statusCode; //编码
    private String msgId; //返回编码
    private Map<String ,String> msgParams; //错误信息
    private ResultType resultType; //返回类型

    public Result() {
    }

    public Result(T result,String msgId,  ResultType resultType,StatusCode statusCode,Map<String, String> msgParams) {
        this.result = result;
        this.statusCode = statusCode;
        this.msgId = msgId;
        this.msgParams = msgParams;
        this.resultType = resultType;
    }

    public static <T> Result<T> getMessageResult(T res, String msgId) {
        return new Result<T>(res, msgId, ResultType.MESSAGE, StatusCode.C200, null);
    }

    public static <T> Result<T> getSuccessResult(T res) {
        return new Result<T>(res, null, ResultType.SUCCESS, StatusCode.C200, null);
    }

    public static <T> Result<T> getSuccessResult(T res, String msgId, Map<String,String> msgParams) {
        return new Result<T>(res, msgId, ResultType.SUCCESS, StatusCode.C200, msgParams);
    }
    public static <T> Result<T> getErrorResult(String msgId) {
        return new Result<T>(null, msgId, ResultType.ERROR, StatusCode.C500, null);
    }

    public static <T> Result<T> getNotFoundResult() {
        return new Result<T>(null, null, ResultType.NOT_FOUND, StatusCode.C500, null);
    }
}
