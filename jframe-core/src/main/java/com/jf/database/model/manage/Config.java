package com.jf.database.model.manage;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-08-06
 * Time: 11:45
 */
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 键 */
    private String key;

    /** 值 */
    private String val;

    /** 键说明 */
    private String descr;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "Config{" +
                "key='" + key + '\'' +
                ", val='" + val + '\'' +
                ", descr='" + descr + '\'' +
                '}';
    }
}
