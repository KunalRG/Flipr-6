package com.pentagon.android.Object;

public class Hospital {
    private String State;
    private String RuralBeds;
    private String RuralHospitals;
    private String UrbanBeds;
    private String UrbanHospitals;
    private String TotalHospitals;
    private String ToralBeds;

    public Hospital(String state, String ruralBeds, String ruralHospitals, String urbanBeds, String urbanHospitals, String totalHospitals, String toralBeds) {
        State = state;
        RuralBeds = ruralBeds;
        RuralHospitals = ruralHospitals;
        UrbanBeds = urbanBeds;
        UrbanHospitals = urbanHospitals;
        TotalHospitals = totalHospitals;
        ToralBeds = toralBeds;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getRuralBeds() {
        return RuralBeds;
    }

    public void setRuralBeds(String ruralBeds) {
        RuralBeds = ruralBeds;
    }

    public String getRuralHospitals() {
        return RuralHospitals;
    }

    public void setRuralHospitals(String ruralHospitals) {
        RuralHospitals = ruralHospitals;
    }

    public String getUrbanBeds() {
        return UrbanBeds;
    }

    public void setUrbanBeds(String urbanBeds) {
        UrbanBeds = urbanBeds;
    }

    public String getUrbanHospitals() {
        return UrbanHospitals;
    }

    public void setUrbanHospitals(String urbanHospitals) {
        UrbanHospitals = urbanHospitals;
    }

    public String getTotalHospitals() {
        return TotalHospitals;
    }

    public void setTotalHospitals(String totalHospitals) {
        TotalHospitals = totalHospitals;
    }

    public String getToralBeds() {
        return ToralBeds;
    }

    public void setToralBeds(String toralBeds) {
        ToralBeds = toralBeds;
    }
}
