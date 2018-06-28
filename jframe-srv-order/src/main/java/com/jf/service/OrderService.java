package com.jf.service;

import com.jf.database.mapper.UserMapper;
import com.jf.database.model.User;
import com.jf.string.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-02-27
 * Time: 10:19
 */
@Service
public class OrderService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    /**
     * 下单
     *
     * @param userId
     * @param productId
     * @param price
     * @param amount
     * @return
     */
    public Order order(Long userId, Long productId, Double price, Integer amount) {
        String orderNum = StringUtil.getOrderCode();
        Order order = new Order(userId, productId, price, amount, orderNum);
        order.setCreateTime(new Date());
        return order;
    }


    /**
     * 分布式事务 - 增加余额
     *
     * @param xid
     * @param money
     */
    public void add(String xid, String money) {
        long startTime = System.currentTimeMillis();   //获取开始时间

        User user = new User(10001);
        user.setMoney(150d);
        userMapper.update(user);

        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("运行时间： " + (endTime - startTime) + "ms");
    }

    /**
     * 分布式事务 - 减少余额
     *
     * @param xid
     * @param money
     */
    public void reduce(String xid, String money) {
        long startTime = System.currentTimeMillis();   //获取开始时间

        User user = new User(10000);
        user.setMoney(50d);
        userMapper.update(user);

        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("运行时间： " + (endTime - startTime) + "ms");
    }

    class Order {
        private Long userId;

        private Long productId;

        private Double price;

        private Integer amount;

        private String orderNum;

        private Date createTime;

        public Order() {
        }

        public Order(Long userId, Long productId, Double price, Integer amount, String orderNum) {
            this.userId = userId;
            this.productId = productId;
            this.price = price;
            this.amount = amount;
            this.orderNum = orderNum;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }
    }

}
