package cn.edu.ncu.collegesecondhand.entity;

import java.math.BigDecimal;

/**
 * Created by ren lingyun on 2020/4/20 0:08
 */
public class CartBean {
    private int id;//cart id
    private int productId;
    private String userAvatar;
    private String userName;
    private String productImages;
    private String productCover;//Service transforms images into cover
    private String productName;
    private BigDecimal productPrice;
    private BigDecimal carryFee;

    private boolean isChecked=false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public CartBean() {
    }

    public CartBean(int id, String userAvatar, String userName, String productImages, String productCover, String productName, BigDecimal productPrice, BigDecimal carryFee) {
        this.id = id;
        this.userAvatar = userAvatar;
        this.userName = userName;
        this.productImages = productImages;
        this.productCover = productCover;
        this.productName = productName;
        this.productPrice = productPrice;
        this.carryFee = carryFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public String getProductCover() {
        return productCover;
    }

    public void setProductCover(String productCover) {
        this.productCover = productCover;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getCarryFee() {
        return carryFee;
    }

    public void setCarryFee(BigDecimal carryFee) {
        this.carryFee = carryFee;
    }
}
