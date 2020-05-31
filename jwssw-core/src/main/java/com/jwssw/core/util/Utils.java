package com.jwssw.core.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.jwssw.core.handle.BeanHandle;
import com.jwssw.core.handle.UUIDHandle;
import com.jwssw.core.oauth2.MyUserDetail;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * 工具类集合
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 14:45
 * @since JDK 11
 */
@Builder(access = AccessLevel.PRIVATE)
public class Utils {

    /**
     * UUID 工具类
     */
    public static class UuidUtil extends UUIDHandle {
    }

    /**
     * java bean相关处理工具类
     */
    public static class BeanUtil extends BeanHandle {
    }


    /**
     * spring security相关安全工具类
     */
    public static class SecurityUtil {
        /**
         * 获取当前登录信息
         *
         * @return 当前用户信息
         */
        public static Authentication getAuthentication() {
            if (ObjectUtil.isNull(SecurityContextHolder.getContext())) {
                return null;
            }

            return SecurityContextHolder.getContext().getAuthentication();
        }

        /**
         * 获取当前用户名
         *
         * @return 当前用户名
         */
        public static String getUserName() {
            Authentication authentication = getAuthentication();
            if (ObjectUtil.isNotNull(authentication)) {
                Object principal = authentication.getPrincipal();
                if (ObjectUtil.isNotNull(principal) && principal instanceof User) {
                    return ((User) principal).getUsername();
                } else if (principal instanceof MyUserDetail) {
                } else {
                    return StrUtil.toString(principal);
                }
            }
            return null;
        }
    }

}
