package com.siddhi.skynet.Admin;

/**
 * Created by Prakash on 6/26/2021.
 */
public class ProfileModel {

    String Name;
    String Number;
    String FullAddress;
    String EmailAddress;
    String dateCreated;

    public ProfileModel(String name, String number, String fullAddress, String emailAddress, String dateCreated) {
        Name = name;
        Number = number;
        FullAddress = fullAddress;
        EmailAddress = emailAddress;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getFullAddress() {
        return FullAddress;
    }

    public void setFullAddress(String fullAddress) {
        FullAddress = fullAddress;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
