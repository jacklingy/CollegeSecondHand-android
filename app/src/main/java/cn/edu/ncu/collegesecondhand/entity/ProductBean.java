package cn.edu.ncu.collegesecondhand.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by ren lingyun on 2020/4/16 0:55
 */

//to display at home page
public class ProductBean implements Serializable {
    //product
    private int id;
    private String images;
    private String productCover;
    private String productName;
    private BigDecimal productPrice;

    private String userAvatar;//seller
    private String userName;
    private int isBadge;
    private int isBadgeAdmin;
    private int isBadgeMember;
    private int isBadgeNcu;

    public ProductBean() {
    }

    public ProductBean(int id, String images, String productCover, String productName, BigDecimal productPrice, String userAvatar, String userName, int isBadge, int isBadgeAdmin, int isBadgeMember, int isBadgeNcu) {
        this.id = id;
        this.images = images;
        this.productCover = productCover;
        this.productName = productName;
        this.productPrice = productPrice;
        this.userAvatar = userAvatar;
        this.userName = userName;
        this.isBadge = isBadge;
        this.isBadgeAdmin = isBadgeAdmin;
        this.isBadgeMember = isBadgeMember;
        this.isBadgeNcu = isBadgeNcu;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public int getIsBadge() {
        return isBadge;
    }

    public void setIsBadge(int isBadge) {
        this.isBadge = isBadge;
    }

    public int getIsBadgeAdmin() {
        return isBadgeAdmin;
    }

    public void setIsBadgeAdmin(int isBadgeAdmin) {
        this.isBadgeAdmin = isBadgeAdmin;
    }

    public int getIsBadgeMember() {
        return isBadgeMember;
    }

    public void setIsBadgeMember(int isBadgeMember) {
        this.isBadgeMember = isBadgeMember;
    }

    public int getIsBadgeNcu() {
        return isBadgeNcu;
    }

    public void setIsBadgeNcu(int isBadgeNcu) {
        this.isBadgeNcu = isBadgeNcu;
    }
}
