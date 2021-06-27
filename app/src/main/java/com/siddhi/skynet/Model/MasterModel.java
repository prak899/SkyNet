package com.siddhi.skynet.Model;

public class MasterModel {
    String serviceName, servicePrice, checkService;
    String serviceType, image;

    public MasterModel() {
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

    public String getCheckService() {
        return checkService;
    }

    public void setCheckService(String checkService) {
        this.checkService = checkService;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
