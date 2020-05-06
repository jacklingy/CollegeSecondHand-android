package cn.edu.ncu.collegesecondhand.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ren lingyun on 2020/4/15 0:52
 */
public class Product implements Serializable {
    private int id;
    private String name;
    private String description;
    private String images;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private BigDecimal carryFee;
    private int userId;
    private int categoryId;
    private String sourceAddress;
    private String createdTime;
    private int status;

    public int getStatus() {
        return status;
    }

    public Product() {
    }

    public Product(String name, String description, String images, BigDecimal price, BigDecimal originalPrice, BigDecimal carryFee, int userId, int categoryId, String sourceAddress) {
        this.name = name;
        this.description = description;
        this.images = images;
        this.price = price;
        this.originalPrice = originalPrice;
        this.carryFee = carryFee;
        this.userId = userId;
        this.categoryId = categoryId;
        this.sourceAddress = sourceAddress;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getCarryFee() {
        return carryFee;
    }

    public void setCarryFee(BigDecimal carryFee) {
        this.carryFee = carryFee;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
