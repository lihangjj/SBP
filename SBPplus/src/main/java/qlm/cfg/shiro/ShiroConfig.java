package qlm.cfg.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import qlm.realm.CustomerCredentialsMatcher;
import qlm.realm.MemberRealm;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public MemberRealm getRealm() {// 1、获取配置的Realm，之所以没使用注解配置，是因为此处需要考虑到加密处理
        MemberRealm realm = new MemberRealm();
        realm.setCredentialsMatcher(new CustomerCredentialsMatcher());
        return realm;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public EhCacheManager getCacheManager() {// 2、缓存配置
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    @Bean
    public SessionIdGenerator getSessionIdGenerator() { // 3
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public SessionDAO getSessionDAO(SessionIdGenerator sessionIdGenerator) { // 4
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        return sessionDAO;
    }

    @Bean
    public RememberMeManager getRememberManager() { // 5
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        SimpleCookie cookie = new SimpleCookie("QLM-RememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        rememberMeManager.setCookie(cookie);
        return rememberMeManager;
    }

    @Bean
    public QuartzSessionValidationScheduler getQuartzSessionValidationScheduler() {
        QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler();
        sessionValidationScheduler.setSessionValidationInterval(100000);
        return sessionValidationScheduler;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    @Bean
    public DefaultWebSessionManager getSessionManager(SessionDAO sessionDAO,
                                                      QuartzSessionValidationScheduler sessionValidationScheduler) { // 6
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1000000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionDAO(sessionDAO);
        SimpleCookie sessionIdCookie = new SimpleCookie("qlm-session-id");
        sessionIdCookie.setHttpOnly(true);
        sessionIdCookie.setMaxAge(-1);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager getSecurityManager(Realm memberRealm, EhCacheManager cacheManager,
                                                        SessionManager sessionManager, RememberMeManager rememberMeManager) {// 7
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(memberRealm);
        securityManager.setCacheManager(cacheManager);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    public FormAuthenticationFilter getLoginFilter() { // 在ShiroFilterFactoryBean中使用
        FormAuthenticationFilter filter = new FormAuthenticationFilter();
        filter.setUsernameParam("memberid");
        filter.setPasswordParam("password");
        filter.setRememberMeParam("rememberMe");
        filter.setLoginUrl("/login");    // 设置用于认证用户的登录URL
        filter.setFailureKeyAttribute("error");
        return filter;
    }

    public LogoutFilter getLogoutFilter() { // 在ShiroFilterFactoryBean中使用
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/");    // 首页路径，登录注销后回到的页面
        return logoutFilter;
    }

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/loginPage");    // 登陆错误之后，去访问登录页
        shiroFilterFactoryBean.setSuccessUrl("/pages/back/loginSuccess");    // 设置登陆成功执行方法的路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthUrl");    // 没有授权，后执行的方法

        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc", this.getLoginFilter());
        filters.put("logout", this.getLogoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/login", "authc");    // 定义内置登录处理
        filterChainDefinitionMap.put("/pages/back/**", "authc");
        filterChainDefinitionMap.put("/*", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}
