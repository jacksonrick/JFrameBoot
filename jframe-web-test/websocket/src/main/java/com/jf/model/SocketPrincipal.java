package com.jf.model;

import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-03-08
 * Time: 15:30
 */
public class SocketPrincipal implements Principal {

    // 自定义的用户实体类，实现PrincipalgetName方法
    private User user;

    public SocketPrincipal(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /**
     * 可自定义绑定的用户id
     * 即发送对象的标识
     *
     * @return
     */
    @Override
    public String getName() {
        return String.valueOf(user.getId());
    }

}