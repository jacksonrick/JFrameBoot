package com.jf.system.handler.rest;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-07-10
 * Time: 15:37
 */
public class MyJsonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public MyJsonHttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);  // 加入text/html类型的支持
        setSupportedMediaTypes(mediaTypes);
    }

}
