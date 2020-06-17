package com.jf;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.jf.model.Foo;
import com.jf.model.Foo2;
import com.jf.model.FooNacosConfigConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description: Springboot 配置管理
 * User: xujunfei
 * Date: 2019-03-04
 * Time: 15:25
 */
@RestController
@NacosPropertySource(dataId = "user3", groupId = "MYGROUP", autoRefreshed = true) // 配合NacosValue注解使用
public class SpringBootController {

    /**
     * 监听
     *
     * @param foo
     */
    @NacosConfigListener(dataId = "user", groupId = "MYGROUP", converter = FooNacosConfigConverter.class)
    public void onChange(Foo foo) {
        System.out.print("监听到配置变化: ");
        System.out.println(foo);
    }

    // 键值对映射
    @Autowired
    private Foo2 foo2;

    @GetMapping("/getFoo")
    public Object getFoo() {
        return foo2;
    }

    // NacosValue注解
    // autoRefreshed自动刷新，@NacosPropertySource也需要设置为true
    @NacosValue(value = "${open:false}", autoRefreshed = true)
    private boolean open;

    @GetMapping("/getOpen")
    public Object getOpen() {
        return open;
    }

}
