package com.jf;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.jf.database.model.Foo;
import com.jf.database.model.Foo2;
import com.jf.database.model.FooNacosConfigConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-03-04
 * Time: 15:25
 */
@RestController
@NacosPropertySource(dataId = "user", groupId = "com.jf")
public class TestController {

    /**
     * 监听
     *
     * @param foo
     */
    @NacosConfigListener(dataId = "user", groupId = "com.jf", converter = FooNacosConfigConverter.class)
    public void onChange(Foo foo) {
        System.out.println(foo);
    }

    // 键值对映射
    @Autowired
    private Foo2 foo2;

    @GetMapping("/test")
    public Object test() {
        return foo2;
    }

}
