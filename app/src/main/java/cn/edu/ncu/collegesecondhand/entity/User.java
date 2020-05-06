package cn.edu.ncu.collegesecondhand.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ren lingyun on 2020/4/14 18:09
 */
public class User implements Serializable {
    private int id;
    private String account;
    private String password;
    private String nickName;
    private String gender;
    private Date birthday;
    private String register_time;
    private String phone;
    private String avatar;
    private String college;
    private BigDecimal credit;
    private int isBadge;
    private int isAdmin;
    private int isMember;
    private int isNcu;//0-no,1-yes;

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public int getIsBadge() {
        return isBadge;
    }

    public void setIsBadge(int isBadge) {
        this.isBadge = isBadge;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public int getIsNcu() {
        return isNcu;
    }

    public void setIsNcu(int isNcu) {
        this.isNcu = isNcu;
    }

    public User() {
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
