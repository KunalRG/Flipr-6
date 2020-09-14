package com.pentagon.android.Object;

public class College {
    private String state;
    private String name;
    private String city;
    private String ownership;
    private String admissionCapacity;
    private String hospitalBeds;

    public College(String state, String name, String city, String ownership, String admissionCapacity, String hospitalBeds) {
        this.state = state;
        this.name = name;
        this.city = city;
        this.ownership = ownership;
        this.admissionCapacity = admissionCapacity;
        this.hospitalBeds = hospitalBeds;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getAdmissionCapacity() {
        return admissionCapacity;
    }

    public void setAdmissionCapacity(String admissionCapacity) {
        this.admissionCapacity = admissionCapacity;
    }

    public String getHospitalBeds() {
        return hospitalBeds;
    }

    public void setHospitalBeds(String hospitalBeds) {
        this.hospitalBeds = hospitalBeds;
    }
}
