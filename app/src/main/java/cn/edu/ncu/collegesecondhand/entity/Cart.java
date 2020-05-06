package cn.edu.ncu.collegesecondhand.entity;

import java.io.Serializable;

/**
 * Created by ren lingyun on 2020/4/15 1:03
 */
public class Cart implements Serializable {
    private int id;
    private int userId;
    private int productId;

    public Cart() {
    }

    public Cart(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
