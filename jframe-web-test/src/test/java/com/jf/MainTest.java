package com.jf;

import com.jf.po.TestUser;

import java.net.URI;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-04-02
 * Time: 17:12
 */
public class MainTest {

    public static void main(String[] args) {
        Integer a = 100;
        Integer b = 100;
        System.out.println(a == b);

        Integer s = null;
        System.out.println(String.valueOf(s));

        double d1 = 36.73;
        double d2 = 32.65;
        System.out.println(d1 + d2);

        float f1 = 36.73f;
        float f2 = 32.65f;
        System.out.println(f1 + f2);
    }

    public static void testLink() {
        TestUser user = new TestUser()
                .id(1)
                .username("xu")
                .age(10)
                .createTime(new Date())
                .enable(true);
        System.out.println(user);
    }

    public static URI testURI(String uri) {
        URI u = URI.create(uri);
        //Let's assuming most of the time it is OK.
        if (u.getHost() != null) {
            return u;
        }
        String s = uri.substring(0, uri.lastIndexOf(":")).replaceFirst("redis://", "");
        //Assuming this is an IPv6 format, other situations will be handled by
        //Netty at a later stage.
        return URI.create(uri.replace(s, "[" + s + "]"));
    }

}
