package com.jf.controller;

import com.jf.entity.ResMsg;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xujunfei on 2017/4/7.
 */
@RestController
@RequestMapping("/app")
public class IndexAppController extends BaseController {

    @GetMapping("/home")
    public ResMsg home() {
        return new ResMsg(0, SUCCESS);
    }

    @GetMapping("/online")
    public ResMsg online() {
        return new ResMsg(0, "SUCCESS", true);
    }

}
