package com.siddhi.skynet.Model;

public class OrderModel {
    private String productPrice;
    private String productId;
    private String coustomerNumber;
    private String discount;
    private String orderId;
    private String status;
    private String statusId;
    private String coustomerAddress;
    private String esimateTime;
    private String orderDate;


    public OrderModel(String productPrice, String productId, String coustomerNumber, String discount, String orderId, String status, String statusId, String coustomerAddress, String esimateTime, String orderDate) {
        this.productPrice = productPrice;
        this.productId = productId;
        this.coustomerNumber = coustomerNumber;
        this.discount = discount;
        this.orderId = orderId;
        this.status = status;
        this.statusId = statusId;
        this.coustomerAddress = coustomerAddress;
        this.esimateTime = esimateTime;
        this.orderDate = orderDate;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCoustomerNumber() {
        return coustomerNumber;
    }

    public void setCoustomerNumber(String coustomerNumber) {
        this.coustomerNumber = coustomerNumber;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getCoustomerAddress() {
        return coustomerAddress;
    }

    public void setCoustomerAddress(String coustomerAddress) {
        this.coustomerAddress = coustomerAddress;
    }

    public String getEsimateTime() {
        return esimateTime;
    }

    public void setEsimateTime(String esimateTime) {
        this.esimateTime = esimateTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
