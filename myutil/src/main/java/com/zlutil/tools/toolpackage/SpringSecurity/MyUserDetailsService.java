package com.zlutil.tools.toolpackage.SpringSecurity;

import com.zlutil.tools.toolpackage.SpringSecurity.Dao.UserDTO;
import com.zlutil.tools.toolpackage.SpringSecurity.Dao.UserEntity;
import com.zlutil.tools.toolpackage.SpringSecurity.Dao.UserRepositry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * 此配置可以和数据库里的数据进行交互
 * <p>
 * 此配置对应的Config配置为：
 * //    @Bean
 * //    public UserDetailsService userDetailsService(){
 * //        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
 * //        manager.createUser(User.withUsername("user").password("123456").roles("user").build());
 * //        return manager;
 * //    }
 * <p>
 * 或者
 * <p>
 * //    @Override
 * //    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 * //        //配置用户名，登录名，角色
 * //        auth.inMemoryAuthentication()
 * //                .withUser("user")
 * //                .password("123456")
 * //                .roles("admin");
 * //    }
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositry userRepositry;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepositry.findByUsername(username);
        if (Objects.isNull(userEntity)) {
            throw new RuntimeException();
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);

        userDTO.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(userEntity.getRole()));//String转List工具
        return userDTO;
    }
}
