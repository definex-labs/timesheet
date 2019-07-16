package com.definex.enterprise.app.timesheet.view;

import java.util.List;

public class TimeRowPojo {

    private String chargeCode;

    private int[] hours;

    private List<HourPojo> hourPojoList;

    public TimeRowPojo() {
        hours = new int[16];
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public int[] getHours() {
        return hours;
    }

    public void setHours(int[] hours) {
        this.hours = hours;
    }

    public void setHour(int index, int value) {
        hours[index] = value;
    }

    public List<HourPojo> getHourPojoList() {
        return hourPojoList;
    }

    public void setHourPojoList(List<HourPojo> hourPojoList) {
        this.hourPojoList = hourPojoList;
    }
}
