package com.jf;

import com.jf.model.Admin;
import com.jf.model.Product;
import com.jf.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@SpringBootApplication
@Controller
public class RedisApplication {

    @GetMapping("/")
    @ResponseBody
    public String index(HttpSession session) {
        Admin admin = new Admin();
        admin.setId(1001l);
        admin.setUsername("xujf");
        admin.setAge(10);
        admin.setDisabled(false);
        admin.setRoles("SUPER");
        admin.setCreateTime(new Date());
        session.setAttribute("admin", admin);
        return "index";
    }

    @GetMapping("/getuser")
    @ResponseBody
    public Object getuser(HttpSession session) {
        return session.getAttribute("admin");
    }

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/set")
    @ResponseBody
    public String set() {
        stringRedisTemplate.opsForValue().set("spring:uid", "10001");
        stringRedisTemplate.opsForValue().set("spring:name", "xujf");

        Product product1 = new Product();
        product1.setId(1001l);
        product1.setName("apple");
        product1.setCurMoney(new BigDecimal("19.9"));
        product1.setDisabled(false);
        product1.setCreateTime(new Date());
        Product product2 = new Product();
        product2.setId(1002l);
        product2.setName("pair");
        product2.setCurMoney(new BigDecimal("99.9"));
        product2.setDisabled(true);
        product2.setCreateTime(new Date());
        redisTemplate.opsForHash().put("spring:hash", "product1", product1);
        redisTemplate.opsForHash().put("spring:hash", "product2", product2);

        redisTemplate.opsForSet().add("spring:set", product1, product2);
        return "success";
    }

    @GetMapping("/get")
    @ResponseBody
    public String get() {
        String uid = (String) stringRedisTemplate.opsForValue().get("spring:uid");
        System.out.println(uid);
        String name = (String) stringRedisTemplate.opsForValue().get("spring:name");
        System.out.println(name);

        Product product = (Product) redisTemplate.opsForHash().get("spring:hash", "product1");
        System.out.println(product);

        Set sets = redisTemplate.opsForSet().members("spring:set");
        System.out.println(sets);
        return "success";
    }


    @Resource
    private ProductService productService;

    @GetMapping("/find")
    @ResponseBody
    public Object find() {
        Product pro = productService.findById3(1001l);
        return pro;
    }

    @GetMapping("/delete")
    @ResponseBody
    public String delete() {
        productService.deleteById(1001l);
        return "success";
    }


    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout success";
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

}
