package com.siddhi.skynet.Model;

public class CartModel {
    String serviceName, servicePrice, paymentStatus, serviceType, userNumber, userEmail, userAddress;

    private String esimateTime;
    private String orderDate;
    private String orderQuantity;
    private String serviceImage;

    public CartModel() {
    }

    public CartModel(String serviceName, String servicePrice, String paymentStatus, String serviceType, String userNumber, String userEmail, String userAddress, String esimateTime, String orderDate, String orderQuantity, String serviceImage) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.paymentStatus = paymentStatus;
        this.serviceType = serviceType;
        this.userNumber = userNumber;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.esimateTime = esimateTime;
        this.orderDate = orderDate;
        this.orderQuantity = orderQuantity;
        this.serviceImage = serviceImage;
    }



    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
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

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }
}
