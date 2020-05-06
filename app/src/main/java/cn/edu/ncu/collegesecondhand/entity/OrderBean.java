package cn.edu.ncu.collegesecondhand.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by ren lingyun on 2020/4/22 18:04
 */

//提交未付款0，付款未收货1，确认收货完成2，产生退货3，退货完成4
public class OrderBean implements Serializable {
    /**
     * 不要考虑几个商品组成一个订单，每个商品就是一个订单；
     * 订单页面仅显示基本信息出来，点击会显示订单详情；
     */
    //
    //order
    private int id;
    private int productId;
    private int sellerId;
    private int addressId;
    private int status;
    private int userId;
    private String orderNumber;
    private String createdTime;
    private int payment;
    private String payTime;
    private String finishTime;
    //product
    private String productName;
    private String productCover;
    private BigDecimal productPrice;
    private BigDecimal productCarryFee;
    private BigDecimal productOriginalPrice;
    //seller
    private String sellerName;
    private String sellerAvatar;
    //address
    private String addressName;
    private String addressAddress;
    private String addressPhone;
    public OrderBean() {
    }

    public OrderBean(int id, int productId, int sellerId, int addressId, int status, int userId, String orderNumber, String createdTime, int payment, String payTime, String finishTime, String productName, String productCover, BigDecimal productPrice, BigDecimal productCarryFee, BigDecimal productOriginalPrice, String sellerName, String sellerAvatar, String addressName, String addressAddress, String addressPhone) {

        this.id = id;
        this.productId = productId;
        this.sellerId = sellerId;
        this.addressId = addressId;
        this.status = status;
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.createdTime = createdTime;
        this.payment = payment;
        this.payTime = payTime;
        this.finishTime = finishTime;
        this.productName = productName;
        this.productCover = productCover;
        this.productPrice = productPrice;
        this.productCarryFee = productCarryFee;
        this.productOriginalPrice = productOriginalPrice;
        this.sellerName = sellerName;
        this.sellerAvatar = sellerAvatar;
        this.addressName = addressName;
        this.addressAddress = addressAddress;
        this.addressPhone = addressPhone;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCover() {
        return productCover;
    }

    public void setProductCover(String productCover) {
        this.productCover = productCover;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductCarryFee() {
        return productCarryFee;
    }

    public void setProductCarryFee(BigDecimal productCarryFee) {
        this.productCarryFee = productCarryFee;
    }

    public BigDecimal getProductOriginalPrice() {
        return productOriginalPrice;
    }

    public void setProductOriginalPrice(BigDecimal productOriginalPrice) {
        this.productOriginalPrice = productOriginalPrice;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerAvatar() {
        return sellerAvatar;
    }

    public void setSellerAvatar(String sellerAvatar) {
        this.sellerAvatar = sellerAvatar;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressAddress() {
        return addressAddress;
    }

    public void setAddressAddress(String addressAddress) {
        this.addressAddress = addressAddress;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }
}
