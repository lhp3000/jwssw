package com.jwssw.auth.userdetails.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户验证VO
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 11:57
 * @since JDK 11
 */
@Data
@Accessors(chain = true)
public class LoginVO {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String captcha;
}
