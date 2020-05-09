package com.jf.system.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-03-22
 * Time: 14:50
 */
public class Constant {

    public final static String APPID = "wx8cdde2f894dc5d34";
    public final static String SECRET = "1d3e36dff6251390a92bc66020787b85";

    public final static String SESSIONID = "sessionid";
    public final static String TOKEN = "token";

    public static List<String> KEYS = new ArrayList<>();
    public final static String STR_UID = "uid";
    public final static String STR_CONFIRM = "confirm";
    public final static String STR_TYPE = "value";
    public final static String STR_STATE = "state";
    public final static String STR_EXPIRE = "expire";

    static {
        KEYS.add(STR_UID);
        KEYS.add(STR_CONFIRM);
        KEYS.add(STR_TYPE);
    }


    // value: login,bind,...
    public enum Type {
        Login("login", "登陆"),
        Bind("bind", "绑定");

        Type(String type, String typename) {
            this.type = type;
            this.typename = typename;
        }

        private String type;
        private String typename;

        public String value() {
            return type;
        }

        public String typename() {
            return typename;
        }

        public static String get(String type) {
            for (Type s : Type.values()) {
                if (s.value().equals(type)) {
                    return s.typename();
                }
            }
            return "--";
        }

    }

    // 0-初始值 1-已扫码 2-确认 3-取消
    public enum Confirm {
        Scaning("0"),
        Scaned("1"),
        Confirmed("2"),
        Cancel("3");

        Confirm(String code) {
            this.code = code;
        }

        private String code;

        public String value() {
            return code;
        }

    }

}
