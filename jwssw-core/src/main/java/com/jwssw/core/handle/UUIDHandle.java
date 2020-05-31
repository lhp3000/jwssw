package com.jwssw.core.handle;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Sequence;

/**
 * Id工具类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/2/4 12:39
 * @since JDK 11
 */
public class UUIDHandle extends IdWorker {

    /**
     * 日期 ID = Date + ID
     *
     * @return id
     */
    public static String getDateId() {
        return DatePattern.PURE_DATE_FORMAT.format(DateUtil.date()) + getIdStr();
    }

}
