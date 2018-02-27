package com.jf.service.order;

import com.jf.entity.ResMsg;
import com.jf.string.StringUtil;
import org.springframework.stereotype.Service;

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
