package kg.task.dreamfield.config.security

import org.springframework.beans.factory.config.MethodInvokingFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import java.util.*
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 28800)
class WebSecurityConfig(
        private val sessionRegistry: Optional<SessionRegistry>
) : WebSecurityConfigurerAdapter() {

    private companion object {
        private val SWAGGER_ENDPOINTS = arrayOf("/v2/api-docs", "/configuration/ui/**", "/swagger-resources/**", "/configuration/security/**", "/swagger-ui.html", "/webjars/**")
        private const val AUTH_ENDPOINTS = "/api/v1/auth/**"
        private const val COMMON_ENDPOINTS = "/api/v1/common/**"
    }

    @Bean
    fun methodInvokingFactoryBean(): MethodInvokingFactoryBean {
        val methodInvokingFactoryBean = MethodInvokingFactoryBean()
        methodInvokingFactoryBean.targetClass = SecurityContextHolder::class.java
        methodInvokingFactoryBean.targetMethod = "setStrategyName"
        methodInvokingFactoryBean.setArguments(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL)
        return methodInvokingFactoryBean
    }

    override fun configure(http: HttpSecurity) {
        http.exceptionHandling()
                .authenticationEntryPoint { _, response, _ ->
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                }

        sessionRegistry.ifPresent {
            http
                    .sessionManagement()
                    .maximumSessions(-1)
                    .maxSessionsPreventsLogin(false)
                    .expiredUrl("/logout")
                    .sessionRegistry(it)
        }

        http.cors()
        http.csrf().disable()

                .authorizeRequests()
                .requestMatchers(RequestMatcher { HttpMethod.OPTIONS.matches(it.method) }).permitAll()
                .antMatchers("/**").authenticated()

                .and()

                .formLogin()
                .loginProcessingUrl("/api/v1/login")
                .failureHandler { _, response, _ ->
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                }
                .successHandler { _, response, _ -> response.status = HttpStatus.OK.value() }

                .and()
                .logout()
                .logoutUrl("/api/v1/logout")
                .logoutSuccessHandler(HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers(*SWAGGER_ENDPOINTS)
                .antMatchers(AUTH_ENDPOINTS)
                .antMatchers(COMMON_ENDPOINTS)
    }
}