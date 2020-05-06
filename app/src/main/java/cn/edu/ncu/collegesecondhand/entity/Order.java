package cn.edu.ncu.collegesecondhand.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ren lingyun on 2020/4/15 0:59
 */

//提交未付款0，付款未收货1，确认收货完成2，产生退货5，退货完成6
public class Order {

    private int id;
    private int productId;
    private int sellerId;
    private int addressId;
    private BigDecimal price;
    private BigDecimal carryFee;
    private BigDecimal totalPrice;
    private int status;
    private int userId;
    private String orderNumber;
    private String createdTime;
    private int payment;
    private String payTime;
    private String finishTime;

    public Order() {
    }


    public Order(int productId, int sellerId, int addressId, BigDecimal price, BigDecimal carryFee, BigDecimal totalPrice, int status, int userId, String orderNumber, String createdTime, int payment, String payTime, String finishTime) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.addressId = addressId;
        this.price = price;
        this.carryFee = carryFee;
        this.totalPrice = totalPrice;
        this.status = status;
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.createdTime = createdTime;
        this.payment = payment;
        this.payTime = payTime;
        this.finishTime = finishTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCarryFee() {
        return carryFee;
    }

    public void setCarryFee(BigDecimal carryFee) {
        this.carryFee = carryFee;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}
