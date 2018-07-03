package com.jf;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-05-30
 * Time: 11:42
 */
@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 查询s与数据库中的进行对比
        // 模拟数据库操作
        User user = new User();
        user.setUsername("spring");
        user.setPassword("123456");
        user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        return new CustomUserDetails(user);
    }
}
