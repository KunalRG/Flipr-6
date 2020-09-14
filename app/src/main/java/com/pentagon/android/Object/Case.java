package com.pentagon.android.Object;

public class Case {
    private String patientId;
    private String reportedOn;
    private String ageEstimate;
    private String gender;
    private String state;
    private String status;

    public Case(String patientId, String reportedOn, String ageEstimate, String gender, String state, String status) {
        this.patientId = patientId;
        this.reportedOn = reportedOn;
        this.ageEstimate = ageEstimate;
        this.gender = gender;
        this.state = state;
        this.status = status;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getReportedOn() {
        return reportedOn;
    }

    public void setReportedOn(String reportedOn) {
        this.reportedOn = reportedOn;
    }

    public String getAgeEstimate() {
        return ageEstimate;
    }

    public void setAgeEstimate(String ageEstimate) {
        this.ageEstimate = ageEstimate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
