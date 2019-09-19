package com.jf;

import com.jf.database.model.CustomRole;
import com.jf.database.model.CustomUser;
import com.jf.http.HttpUtils;
import com.jf.json.JacksonUtil;
import com.jf.obj.BeanUtil;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xujunfei on 2016/12/8.
 */
public class MainTest {

    public static void main(String[] args) throws Exception {

    }

    public static void testHttpclient() {
        String str = HttpUtils.doGet("http://127.0.0.1:8888/test", null, null);
        System.out.println(str);
    }

    public static void jsonSerialize() {
        CustomUser user = new CustomUser();
        user.setId(1023193728);
        user.setAge(20);
        user.setName("xxx");
        user.setIds(new Integer[]{100, 102, 103, 104});
        String json = JacksonUtil.objectToJson(user);
        System.out.println(json);
    }

    public static void base64() {
        Base64.Encoder encoder = Base64.getEncoder();
        System.out.println(encoder.encodeToString("hello".getBytes()));
    }

    public static void testReflect() {
        try {
            CustomUser user = new CustomUser();
            user.setAge(10);
            System.out.println(BeanUtil.getEntityValue("age", user));
            BeanUtil.setEntityValue("name", "xujunfei", user);
            System.out.println(user);

            System.out.println(BeanUtil.doMethod("getName", user));
            System.out.println(BeanUtil.doMethod("setName", user, "haha"));
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testBeanToJson() {
        CustomUser user = new CustomUser();
        user.setName("xujunfei");
        user.setAge(10);
        user.setFlag(true);
        user.setBirth(new Date());
        user.setDates(new Date());
        user.setTime(new Date());

        CustomRole role = new CustomRole();
        role.setName("super");
        role.setTime(new Date());
        user.setRole(role);

        String json = JacksonUtil.objectToJson(user);
        System.out.println(json);
    }

    public static void testJsonToBean() {
        String json = "{\"name\":\"xujunfei\",\"age\":10,\"flag\":true,\"birth\":\"2018-06-29\",\"time\":\"2018-06-29 05:02:27\",\"dates\":\"2018-06-29 13:02:27\",\"role\":{\"name\":\"super\",\"time\":\"2018-06-29 13:02:27\"}}";
        CustomUser user = JacksonUtil.jsonToBean(json, CustomUser.class);
        System.out.println(user);
    }

    public static void testJsonToMap() {
        String json = "{\"name\":\"xujunfei\",\"age\":10,\"flag\":true,\"birth\":\"2018-06-29\",\"time\":\"2018-06-29 05:02:27\",\"dates\":\"2018-06-29 13:02:27\",\"role\":{\"name\":\"super\",\"time\":\"2018-06-29 13:02:27\"}}";
        Map map = JacksonUtil.jsonToMap(json);
        System.out.println(map);
    }

    public static void testJsonToList() {
        String json = "[{\"name\":\"xujunfei\",\"age\":10,\"flag\":true,\"birth\":\"2018-06-29\",\"time\":\"2018-06-29 05:02:27\",\"dates\":\"2018-06-29 13:02:27\"},{\"name\":\"feifei\",\"age\":10,\"flag\":true,\"birth\":\"2018-06-29\",\"time\":\"2018-06-29 05:02:27\",\"dates\":\"2018-06-29 13:02:27\"}]";
        List<CustomUser> list = JacksonUtil.jsonToList(json, CustomUser.class);
        System.out.println(list);
    }

    public static void test01() {
        CustomUser user = new CustomUser();
        user.setAge(1);
        user.setFlag(true);
        Map<String, Object> map = BeanUtil.beanToMap(user);
        System.out.println(map);
    }

    public static void test02() {
        System.out.println(MainTest.class.getClassLoader().getResource("logo.png").toString());
    }

    public static void test03() {
        LocalDate start = new LocalDate(2017, 12, 30);
        LocalDate end = new LocalDate(2018, 1, 2);
        int days = Days.daysBetween(start, end).getDays();
        System.out.println(days);
    }

}
