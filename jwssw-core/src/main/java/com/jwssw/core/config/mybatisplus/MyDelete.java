package com.jwssw.core.config.mybatisplus;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 自定义删除方法的处理类
 * <pre>
 *     该系统中的删除仅将表中的数据标志改为删除状态
 * </pre>
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/25 18:30
 * @since JDK 11
 */
public class MyDelete extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String method = "deleteEntity";
        SqlMethod sqlMethod = SqlMethod.UPDATE_BY_ID;
        final String additional = " AND DEL_FLAG='0'";
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                sqlDelSet(),
                tableInfo.getKeyColumn(), tableInfo.getKeyProperty(), additional);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, method, sqlSource);
    }

    /**
     * SQL 更新 set 语句
     *
     * @return sql
     */
    protected String sqlDelSet() {
        String sqlScript = "LAST_UPDATE_TIME=#{lastUpdateTime}," + NEWLINE;
        sqlScript += "LAST_UPDATE_BY=#{lastUpdateBy}," + NEWLINE;
        sqlScript += "DEL_FLAG = '1'";
        sqlScript = SqlScriptUtils.convertSet(sqlScript);
        return sqlScript;
    }
}
