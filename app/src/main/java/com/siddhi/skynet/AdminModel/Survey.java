package com.siddhi.skynet.AdminModel;

public class Survey {
    private String name;
    private String subName;

    public Survey(String name, String subName) {
        this.name = name;
        this.subName = subName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
