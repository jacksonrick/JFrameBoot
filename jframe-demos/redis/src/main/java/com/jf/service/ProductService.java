package com.jf.service;

import com.jf.model.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-06-05
 * Time: 10:59
 */
@Service
public class ProductService {

    @Cacheable(cacheNames = "c1", key = "'product'+#id")
    public Product findById(Long id) {
        System.out.println("findById" + id);
        Product product = new Product();
        product.setId(1001l);
        product.setName("apple");
        product.setCurMoney(new BigDecimal("19.9"));
        product.setDisabled(false);
        product.setCreateTime(new Date());
        return product;
    }

    @Cacheable(cacheNames = "c2", key = "'product'+#id")
    public Product findById2(Long id) {
        System.out.println("findById" + id);
        Product product = new Product();
        product.setId(1001l);
        product.setName("apple");
        product.setCurMoney(new BigDecimal("19.9"));
        product.setDisabled(false);
        product.setCreateTime(new Date());
        return product;
    }

    @Cacheable(cacheNames = "c3", key = "'product'+#id")
    public Product findById3(Long id) {
        System.out.println("findById" + id);
        Product product = new Product();
        product.setId(1001l);
        product.setName("apple");
        product.setCurMoney(new BigDecimal("19.9"));
        product.setDisabled(false);
        product.setCreateTime(new Date());
        return product;
    }

    @CacheEvict(value = "c3", key = "'product'+#id")
    public void deleteById(Long id) {
        System.out.println("deleteById" + id);
    }
}
