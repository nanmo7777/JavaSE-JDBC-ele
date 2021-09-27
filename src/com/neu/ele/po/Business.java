package com.neu.ele.po;

/**
 * @author 林逸科技
 * 商家实体类
 */
public class Business {
    // 商家 ID
    private Integer businessId;
    // 商家密码
    private String password;
    // 商家名
    private String businessName;
    // 商家地址
    private String businessAddress;
    // 商家介绍
    private String businessExplain;
    // 起送费
    private Double starPrice;
    // 配送费
    private Double deliveryPrice;

    /**
     * 方法名: Business (构造方法)
     * 参数: 无参数
     * 修饰符: 无修饰符
     * 范围修饰符: public
     * 返回值类型: 无返回值类型
     * 无参构造方法
     */
    public Business() {
    }

    /**
     * 方法名: Business (构造方法)
     * 参数: Integer businessId, String password, String businessName, String businessAddress, String businessExplain, Double starPrice, Double deliveryPrice
     * 修饰符: 无修饰符
     * 范围修饰符: public
     * 返回值类型: 无返回值类型
     * 有参构造方法
     */
    public Business(Integer businessId, String password, String businessName, String businessAddress, String businessExplain, Double starPrice, Double deliveryPrice) {
        this.businessId = businessId;
        this.password = password;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.businessExplain = businessExplain;
        this.starPrice = starPrice;
        this.deliveryPrice = deliveryPrice;
    }

    // 取值方法
    public Integer getBusinessId() {
        return businessId;
    }
    // 赋值方法
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
    // 取值方法
    public String getPassword() {
        return password;
    }
    // 赋值方法
    public void setPassword(String password) {
        this.password = password;
    }
    // 取值方法
    public String getBusinessName() {
        return businessName;
    }
    // 赋值方法
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    // 取值方法
    public String getBusinessAddress() {
        return businessAddress;
    }
    // 赋值方法
    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }
    // 取值方法
    public String getBusinessExplain() {
        return businessExplain;
    }
    // 赋值方法
    public void setBusinessExplain(String businessExplain) {
        this.businessExplain = businessExplain;
    }
    // 取值方法
    public Double getStarPrice() {
        return starPrice;
    }
    // 赋值方法
    public void setStarPrice(Double starPrice) {
        this.starPrice = starPrice;
    }
    // 取值方法
    public Double getDeliveryPrice() {
        return deliveryPrice;
    }
    // 赋值方法
    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    @Override
    // 重写 toString 方法
    public String toString() {
        return "Business{" +
                "businessId=" + businessId +
                ", password='" + password + '\'' +
                ", businessName='" + businessName + '\'' +
                ", businessAddress='" + businessAddress + '\'' +
                ", businessExplain='" + businessExplain + '\'' +
                ", starPrice=" + starPrice +
                ", deliveryPrice=" + deliveryPrice +
                '}';
    }
}
