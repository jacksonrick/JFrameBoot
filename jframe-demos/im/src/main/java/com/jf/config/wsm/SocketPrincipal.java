package com.jf.config.wsm;

import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-03-08
 * Time: 15:30
 */
public class SocketPrincipal implements Principal {

    private Long id;

    public SocketPrincipal(Long id) {
        this.id = id;
    }

    /**
     * 可自定义绑定的用户id
     * 即发送对象的标识
     *
     * @return
     */
    @Override
    public String getName() {
        return String.valueOf(this.id);
    }

}