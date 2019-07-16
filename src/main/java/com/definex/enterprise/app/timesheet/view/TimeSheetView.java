package com.definex.enterprise.app.timesheet.view;

import com.definex.enterprise.app.timesheet.entities.CalendarInfo;
import com.definex.enterprise.app.timesheet.entities.PeriodStatus;

import java.util.ArrayList;
import java.util.List;

public class TimeSheetView {

    private String period;

    private List<TimeRowPojo> rows;

    private List<CalendarInfo> calenderInfoList;

    private String[] dayStyle;

    private int periodEnd = 15;

    private List<LovPojo> periods;

    private List<LovPojo> wbs;

    private PeriodStatus periodStatus;

    public TimeSheetView() {
        init(null);
    }

    public TimeSheetView(String period) {
        init(period);
    }

    private void init(String period) {
        rows = new ArrayList<>();
        dayStyle = new String[16];
        if (period != null) {
            setPeriod(period);
        }
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setDayStyle(int index, String value) {
        dayStyle[index] = value;
    }

    public List<TimeRowPojo> getRows() {
        return rows;
    }

    public void setRows(List<TimeRowPojo> rows) {
        this.rows = rows;
    }

    public String[] getDayStyle() {
        return dayStyle;
    }

    public void setDayStyle(String[] dayStyle) {
        this.dayStyle = dayStyle;
    }

    public int getPeriodEnd() {
        return calenderInfoList.size() - 1;
    }

    public void setPeriodEnd(int periodEnd) {
        this.periodEnd = periodEnd;
    }

    public List<CalendarInfo> getCalenderInfoList() {
        return calenderInfoList;
    }

    public void setCalenderInfoList(List<CalendarInfo> calenderInfoList) {
        this.calenderInfoList = calenderInfoList;
    }

    public List<LovPojo> getPeriods() {
        return periods;
    }

    public void setPeriods(List<LovPojo> periods) {
        this.periods = periods;
    }

    public List<LovPojo> getWbs() {
        return wbs;
    }

    public void setWbs(List<LovPojo> wbs) {
        this.wbs = wbs;
    }

    public PeriodStatus getPeriodStatus() {
        return periodStatus;
    }

    public void setPeriodStatus(PeriodStatus periodStatus) {
        this.periodStatus = periodStatus;
    }
}
