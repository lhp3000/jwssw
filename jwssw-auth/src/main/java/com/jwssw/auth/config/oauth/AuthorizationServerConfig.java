package com.jwssw.auth.config.oauth;

import com.jwssw.auth.userdetails.UserServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * OAuth2的授权服务配置类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/16 14:12
 * @since JDK 11
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 自定义用户信息
     */
    @Autowired
    public UserServiceDetails userDetailsService;

    /**
     * 授权管理接口
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 数据源
     */
    @Autowired
    DataSource druidDataSource;

    /**
     * 配置OAuth授权服务的安全性
     * <pre>
     *     实际上是指/oauth/token端点。/oauth/authorize端点也需要安全，但这是一个普通的面向用户的端点，
     *     应该以与您的UI其余部分相同的方式进行安全保护，因此这里不做介绍。 根据OAuth2规范的建议，默认设置涵盖了最常见的要求，
     *     因此您无需在此处进行任何操作即可启动基本服务器并运行。
     * </pre>
     *
     * @param security spring security的配置对象
     * @throws Exception 抛出异常
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许客户进行表单身份验证
        security.allowFormAuthenticationForClients();
        // 检查令牌访问
        security.checkTokenAccess("isAuthenticated()");
        // 令牌密钥访问
        security.tokenKeyAccess("isAuthenticated()");
    }

    /**
     * 配置从哪里获取ClientDetails信息。
     * <pre>
     *     目前采用的是从内存中获取ClientDetails信息，后续可以改成从jdbc中获取（使用JdbcClientDetailsService类）
     * </pre>
     *
     * @param clients 客户端信息
     * @throws Exception 抛出异常
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(druidDataSource);
    }

    /**
     * 配置授权服务端点的非安全功能。 例如：令牌存储，令牌自定义，用户批准和授予类型。
     * 默认情况下，您不需要执行任何操作，除非需要密码授予，在这种情况下，您需要提供{@link AuthenticationManager}。
     *
     * @param endpoints 配置授权服务器端点的属性和增强的功能。
     * @throws Exception 抛出异常
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 配置密码授予的AuthenticationManager
        endpoints.authenticationManager(authenticationManager)
                // 配置用户信息
                .userDetailsService(userDetailsService)
                // 配置使用redis存储token
                .tokenStore(tokenStore());
    }

    /**
     * token的存储方式
     * <pre>
     *     使用数据库的方式存储token
     * </pre>
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(druidDataSource);
    }

}
