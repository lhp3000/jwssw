package com.jwssw.core.http;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * REST API 返回结果
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/29 13:28
 * @since JDK 11
 */
@Data
@Accessors(chain = true)
public class HttpResult<T> implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 业务错误码
     */
    private long code;
    /**
     * 结果集
     */
    private T data;
    /**
     * 描述
     */
    private String msg;

    public HttpResult() {
        // 默认成功
        code = HttpStatus.SUCCESS;
    }

    private static <T> HttpResult<T> restResult(T data, long code, String msg) {
        HttpResult<T> apiResult = new HttpResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static <T> HttpResult<T> success(String msg) {
        HttpResult r = new HttpResult();
        r.setMsg(msg);
        return r;
    }

    public static <T> HttpResult<T> success(T data) {
        HttpResult r = new HttpResult();
        r.setData(data);
        return r;
    }

    public static <T> HttpResult<T> success() {
        return new HttpResult();
    }

    public static <T> HttpResult<T> error() {
        return error("未知异常，请联系管理员");
    }

    public static <T> HttpResult<T> error(String msg) {
        return error(HttpStatus.BIZ_ERROR_DEFAULT, msg);
    }

    public static <T> HttpResult<T> error(long code, String msg) {
        HttpResult r = new HttpResult();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    /**
     * 自我判断是否成功
     *
     * @return true表示成功，false表示失败
     */
    public boolean isSuccess() {
        return HttpStatus.SUCCESS == code;
    }

}
