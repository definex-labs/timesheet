package com.definex.enterprise.app.timesheet.view;

import com.definex.enterprise.app.timesheet.entities.CalendarInfo;

import java.util.ArrayList;
import java.util.List;

public class CalendarInfoView {

    private String period;

    private List<CalendarInfo> calenderList;

    public CalendarInfoView() {
        calenderList = new ArrayList<>();
    }

    public void addCalendarInfo(CalendarInfo info) {
        this.calenderList.add(info);
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<CalendarInfo> getCalenderList() {
        return calenderList;
    }

    public void setCalenderList(List<CalendarInfo> calenderList) {
        this.calenderList = calenderList;
    }
}
