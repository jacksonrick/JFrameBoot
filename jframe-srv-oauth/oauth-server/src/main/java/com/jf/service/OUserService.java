package com.jf.service;

import com.jf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 用户服务类
 * User: xujunfei
 * Date: 2018-10-31
 * Time: 15:35
 */
@Service
public class OUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        System.out.println("OUserService ----------------- " + username);

        List<User> list = jdbcTemplate.query("SELECT id, role, name, pwd FROM oauth_user WHERE name = '" + username + "'", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setRoles(rs.getString(2));
                user.setUsername(rs.getString(3));
                user.setPassword(rs.getString(4));
                return user;
            }
        });

        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
