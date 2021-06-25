package com.siddhi.skynet.Model;

public class MasterModel {
    String serviceName, servicePrice, checkService;

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
}
