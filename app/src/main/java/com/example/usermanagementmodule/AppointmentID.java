package com.example.usermanagementmodule;

public class AppointmentID {private String customer;
    private String barber;
    private String service;
    private String dateTime;
    private boolean approved;
    private String ID;

    public AppointmentID(String customer, String barber, String service, String dateTime, boolean approved) {
        this.customer = customer;
        this.barber = barber;
        this.service = service;
        this.dateTime = dateTime;
        this.approved = approved;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBarber() {
        return barber;
    }

    public void setBarber(String barber) {
        this.barber = barber;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "AppointmentID{" +
                "customer='" + customer + '\'' +
                ", barber='" + barber + '\'' +
                ", service='" + service + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", approved=" + approved +
                ", ID='" + ID + '\'' +
                '}';
    }
}
