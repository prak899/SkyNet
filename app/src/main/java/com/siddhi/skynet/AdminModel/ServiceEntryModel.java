package com.siddhi.skynet.AdminModel;

import java.util.Date;

/**
 * Created by Prakash on 6/13/2021.
 */
public class ServiceEntryModel {
    String serviceName;
    String servicePrice;
    String id;
    boolean isImportant;
    String serviceType;


    public ServiceEntryModel(String serviceName, String servicePrice, String id, boolean isImportant, String serviceType) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.id = id;
        this.isImportant = isImportant;
        this.serviceType = serviceType;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
