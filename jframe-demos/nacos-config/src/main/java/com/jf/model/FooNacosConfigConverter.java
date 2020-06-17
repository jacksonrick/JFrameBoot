package com.jf.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.convert.NacosConfigConverter;

/**
 * Created with IntelliJ IDEA.
 * Description: JSON转换器
 * User: xujunfei
 * Date: 2019-03-04
 * Time: 17:04
 */
public class FooNacosConfigConverter implements NacosConfigConverter<Foo> {
    @Override
    public boolean canConvert(Class<Foo> aClass) {
        return true;
    }

    @Override
    public Foo convert(String s) {
        return JSON.parseObject(s, Foo.class);
    }
}
