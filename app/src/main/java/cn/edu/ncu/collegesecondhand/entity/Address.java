package cn.edu.ncu.collegesecondhand.entity;

import java.io.Serializable;

/**
 * Created by ren lingyun on 2020/4/14 18:23
 */
public class Address implements Serializable {
    private int id;
    private String name;
    private String address;
    private String phone;
    private int isDefault;//0-not, 1- is default
    private int userId;
    private String userAccount;
    private int selected;

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public Address() {
    }

    public Address(String name, String address, String phone,String userAccount) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.userAccount=userAccount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
