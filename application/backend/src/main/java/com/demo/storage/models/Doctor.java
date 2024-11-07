package com.demo.storage.models;

public class Doctor {
    private int id;
    private String name;
    private String specialisation;
    private String email;

    public Doctor(){

    }

    public Doctor(int id, String name, String specialisation, String email) {
        this.id = id;
        this.name = name;
        this.specialisation = specialisation;
        this.email = email;
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

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialisation='" + specialisation + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
