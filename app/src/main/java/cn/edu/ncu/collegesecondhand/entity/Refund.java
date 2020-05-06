package cn.edu.ncu.collegesecondhand.entity;

/**
 * Created by ren lingyun on 2020/4/29 2:01
 */
public class Refund {
    private int id;
    private int orderId;
    private String reason;

    public Refund(int orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }

    public Refund() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
