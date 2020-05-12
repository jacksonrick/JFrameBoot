package com.jf.system.aspectsso;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-11-15
 * Time: 14:06
 */
public class Principal<T extends UserDetail> {

    T principal;
    String accessKey;


    public T getPrincipal() {
        return principal;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public Principal set(T principal) {
        this.principal = principal;
        return this;
    }

    public Principal accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public Long getId() {
        return principal.getId();
    }

    public String getName() {
        return principal.getName();
    }

}
