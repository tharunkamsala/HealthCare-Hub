package com.demo.storage.models;

import java.util.Date;

public class Pateint {
    private int id;
    private String name;
    private Date dob;
    private String contactInfo;

    public Pateint(){

    }

    public Pateint(int id, String name, Date dob, String contactInfo) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.contactInfo = contactInfo;
    }

    public Pateint(String name, Date dob, String contactInfo) {
        this.name = name;
        this.dob = dob;
        this.contactInfo = contactInfo;
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

    public Date getDao() {
        return dob;
    }

    public void setDao(Date dob) {
        this.dob = dob;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "Pateint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dao=" + dob +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }
}
