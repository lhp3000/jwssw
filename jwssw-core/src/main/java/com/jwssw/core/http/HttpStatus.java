package com.jwssw.core.http;

/**
 * HTTP status codes
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/29 14:22
 * @since JDK 11
 */
public interface HttpStatus {
    /**
     * 业务正确
     */
    long SUCCESS = 200;
    /**
     * 直接错误返回
     */
    long BIZ_ERROR_DEFAULT = 600;
}
