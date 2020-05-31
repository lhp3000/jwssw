package com.jwssw.resource.config.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 自定义OAuth2用户安全保护的访问规则和路径接口实现类
 * <pre>
 *      @EnableResourceServer类的配置器接口。 实现这个接口来调整由OAuth2用户安全保护的访问规则和路径。
 *      应用程序可以提供此接口的多个实例，通常（与其他安全配置器一样），如果多个配置相同的属性，则最后一个为准。
 *      该configurers由{@link订单}被应用之前进行排序。
 *  </pre>
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/16 14:35
 * @since JDK 11
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    /**
     * 通过配置文件（application.yml）加载OAuth2配置的用户名
     */
    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    /**
     * 通过配置文件（application.yml）加载OAuth2配置用户密钥
     */
    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    /**
     * 通过配置文件（application.yml）加载验证token的URI路径
     */
    @Value("${security.oauth2.authorization.check-token-access}")
    private String checkTokenEndpointUrl;

    /**
     * 创建验证请求中token令牌的远程调用Bean
     * <pre>
     *     通过检查OAuth2服务端（URI路径：/oauth/check_token），来获得访问令牌的内容。
     *     如果OAuth2服务端点返回400，表明此请求中的令牌无效。
     * </pre>
     *
     * @return 远程token验证类
     */
    @Bean
    public RemoteTokenServices tokenService() {
        // 新建远程token验证对象
        RemoteTokenServices tokenService = new RemoteTokenServices();
        // 客户端用户
        tokenService.setClientId(clientId);
        // 客户端密钥
        tokenService.setClientSecret(secret);
        // OAuth2服务端token验证URI路径设置
        tokenService.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
        // 返回远程token验证对象
        return tokenService;
    }

    /**
     * 配置安全资源的访问规则
     * <pre>
     *  默认情况下，所有不在“/oauth/**”路径中的资源都将受到保护（例如，没有给出有关范围的特定规则）。
     *  默认情况下，还可以得到一个OAuth2WebSecurityExpressionHandler默认设置
     * </pre>
     *
     * @param http 当前http过滤配置对象
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // “/actuator/**”路径不受OAuth2的保护，其目的是避免consul对本服务的健康检查被拒绝
        // todo 后续可以在consul中配置OAuth2的访问权限
        // todo 这个地方可以设置白名单（方式：数据库、yml配置文件等）
        http.authorizeRequests().antMatchers("/actuator/**").permitAll()
                .antMatchers("/login/captcha").permitAll();
    }

    /**
     * 配置资源服务器的ID、密钥及token验证URI路径
     * <pre>
     *     添加资源服务器的特定属性（比如资源ID）。 默认值应适用于许多应用程序，但你可能要改变至少资源ID。
     * </pre>
     *
     * @param resources 资源服务器的配置对象
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 在资源服务器中配置token的验证相关的特定属性，便于当前服务器对资源访问的控制
        resources.tokenServices(tokenService());
    }
}
