package com.example.usermanagementmodule;

public class Appointment {
    private String customer;
    private String barber;
    private String service;
    private String dateTime;
    private boolean approved;
    private String ID;

    public Appointment() {
    }

    public Appointment(String customer, String barber, String service, String dateTime, boolean approved) {
        this.customer = customer;
        this.barber = barber;
        this.service = service;
        this.dateTime = dateTime;
        this.approved = approved;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBarber() {
        return barber;
    }

    public void setBarber(String barber) {
        this.barber = barber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    @Override
    public String toString() {
        return "Appointment{" +
                "barber='" + barber + '\'' +
                ", customer='" + customer + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", approved=" + approved +
                '}';
    }
}
