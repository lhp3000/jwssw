package com.jwssw.core.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Mybatisplus的配置类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/25 18:25
 * @since JDK 11
 */
@EnableTransactionManagement
@Configuration
@MapperScan(MybatisPlusConfig.MAPPER_PACKAGE)
@Log4j2
public class MybatisPlusConfig {
    /**
     * Mapper接口
     */
    public static final String MAPPER_PACKAGE = "com.jwssw.**.mapper";
    /**
     * 数据库实体类
     */
    private static final String TYPE_ALIASES_PACKAGE = "com.jwssw.**.entity";
    /**
     * Mapper接口对应的xml
     */
    private static final String CLASSPATH_MAPPER_XML = "classpath*:/mapper/**/*.xml";
    /**
     * 数据库类型
     */
    public static final String DIALECT_TYPE_MYSQL = "mysql";
    /**
     * druid的数据源
     */
    @Autowired
    private DataSource druidDataSource;

    /**
     * 元对象字段填充
     */
    @Autowired
    private MyMetaObjectHandler metaObjectHandler;

    /**
     * 初始化数据库会话
     *
     * @return MybatisSqlSessionFactoryBean
     */
    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() throws Exception {
        // 自定义sql session工厂Bean
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        // 加载xml的映射文件
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(CLASSPATH_MAPPER_XML));
        sessionFactory.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        sessionFactory.setDataSource(druidDataSource);
        // 添加分页插件
        sessionFactory.setPlugins(paginationInterceptor());

        // mybatis配置
        sessionFactory.setConfiguration(mybatisConfiguration());
        // 全局配置
        sessionFactory.setGlobalConfig(globalConfig());

        return sessionFactory;
    }

    /**
     * 创建MybatisConfiguration
     *
     * @return MybatisConfiguration
     */
    public MybatisConfiguration mybatisConfiguration() {
        MybatisConfiguration configuration = new MybatisConfiguration();
        // 是否开启自动驼峰命名规则（camel case）映射
        configuration.setMapUnderscoreToCamelCase(true);
        // 全局地开启或关闭配置文件中的所有映射器已经配置的任何缓存
        configuration.setCacheEnabled(false);
        configuration.setCallSettersOnNulls(true);
        // 配置JdbcTypeForNull, oracle数据库必须配置
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        // MyBatis 自动映射时未知列或未知属性处理策略
        // NONE：不做任何处理 (默认值), WARNING：以日志的形式打印相关警告信息, FAILING：当作映射失败处理，并抛出异常和详细信息
        configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.WARNING);
        // 配置slq打印日志
        configuration.setLogImpl(StdOutImpl.class);

        // mybatis配置对象
        return configuration;
    }

    /**
     * 创建 GlobalConfig
     *
     * @return GlobalConfig
     */
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = GlobalConfigUtils.defaults();
        // 是否打印mybatisplus图像
        globalConfig.setBanner(false);
        // 添加字段填充
        globalConfig.setMetaObjectHandler(metaObjectHandler);
        // 自定义注入SQL语句
        globalConfig.setSqlInjector(new MyInjector());
        // globalConfig.getDbConfig()
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        // 主键类型  0:"数据库ID自增", 1:"未设置主键类型",2:"用户输入ID (该类型可以通过自己注册自动填充插件进行填充)",
        // 3:"全局唯一ID (idWorker), 4:全局唯一ID (UUID), 5:字符串全局唯一ID (idWorker 的字符串表示)";
        dbConfig.setIdType(IdType.NONE);
        // 字段验证策略 IGNORED:"忽略判断", NOT_NULL:"非NULL判断", NOT_EMPTY:"非空判断",
        // DEFAULT 默认的,一般只用于注解里(1. 在全局里代表 NOT_NULL,2. 在注解里代表 跟随全局)
        dbConfig.setSelectStrategy(FieldStrategy.NOT_EMPTY);
        // 数据库大写下划线转换
        dbConfig.setCapitalMode(true);
        // 逻辑删除值
        dbConfig.setLogicDeleteValue("1");
        // 逻辑未删除值
        dbConfig.setLogicNotDeleteValue("0");
        globalConfig.setDbConfig(dbConfig);
        // 返回GlobalConfig配置对象
        return globalConfig;
    }

    /**
     * mybatis-plus分页插件
     *
     * @return 分页插件对象
     */
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

}
