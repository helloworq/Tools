package com.zlutil.tools.toolpackage.SpringSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user").password("123456").roles("user").build());
//        return manager;
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        //不对密码进行加密，便于测试查看
        return NoOpPasswordEncoder.getInstance();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //配置用户名，登录名，角色
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("123456")
//                .roles("admin");
//    }

    @Override
    public void configure(WebSecurity webSecurity) {
        //web.ignoring() 用来配置忽略掉的 URL 地址，一般对于静态文件，我们可以采用此操作。
        webSecurity.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //permitAll 表示登录相关的页面/接口不要被拦截。
        //登录成功回调如果配置successForwardUrl，这时候由于已经过loginProcessingUrl post方法验证
        //所以重定向时会是post方法请求重定向地址，可能出现method not allowed异常
        //logoutRequestMatcher方法不仅可以修改注销 URL，还可以修改请求方式，实际项目中，这个方法和 logoutUrl 任意设置一个即可
        httpSecurity.authorizeRequests()
                .antMatchers("/security/admin/**").hasRole("admin")
                .antMatchers("/security/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/loginCheck")//调用这个接口处理上传数据
                .defaultSuccessUrl("/swagger-ui.html")//成功之后的跳转地址
                .failureUrl("http://www.baidu.com")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .rememberMe()
                .key("security")//预设key防止重启服务器后登录信息失效
                .and()
                .logout()
                //.logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
                .logoutSuccessUrl("/login.html")
                .deleteCookies()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf().disable();
    }

}
