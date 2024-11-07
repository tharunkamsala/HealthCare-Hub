package com.demo.storage.models;
import java.util.Date;

public class Schedule {
    private int doctorId;
    private Date startDate;
    private Date enddate;
    private boolean isAvailable;

    public Schedule(){

    }

    public Schedule(int doctorId, Date startDate, Date enddate, boolean isAvailable) {
        this.doctorId = doctorId;
        this.startDate = startDate;
        this.enddate = enddate;
        this.isAvailable = isAvailable;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
