package com.jwssw.auth.userdetails.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwssw.core.base.entity.MyModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统用户实体类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/26 10:24
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class User extends MyModel {

    /**
     * 表字段静态值
     */
    public static final String NAME = "name";
    public static final String NICK_NAME = "nick_name";
    public static final String AVATAR = "avatar";
    public static final String PASSWORD = "password";
    public static final String SALT = "salt";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String STATUS = "status";
    public static final String DEPT_ID = "dept_id";

    /**
     * 表业务字段
     */
    private String name;
    private String nickName;
    private String avatar;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private String status;
    private String deptId;
}
