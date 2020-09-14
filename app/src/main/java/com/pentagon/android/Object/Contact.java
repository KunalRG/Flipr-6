package com.pentagon.android.Object;

public class Contact {
    String loc;
    String number;

    public Contact(String loc, String number) {
        this.loc = loc;
        this.number = number;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
