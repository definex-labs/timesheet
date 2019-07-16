package com.definex.enterprise.app.timesheet.utility;

import com.definex.enterprise.app.timesheet.view.LovPojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Consts {

    public static final String appName = "Time Sheet";

    public static final String PATH_WBS = "wbs";

    public static final String PATH_PERIOD = "periods";

    public static final String PATH_CALENDAR_INFO = "calendar";

    public static final String PATH_TIMESHEET = "time";

    public enum WbsType {
        BD, PROJECT, NOLBA
    }

    public enum Status {
        ACTIVE, PASSIVE
    }

    public enum AuthRequired {
        YES, NO
    }

    public enum CalenderStatus {
        regular, weekend, holiday
    }

    public enum PeriodStatusType {
        READY, OVERDUE, SUBMITTED, PROCESSED, ADJUSTED
    }

    public enum WeekDays {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
    }

    public enum Months {
        JANUARY, FEBRAURY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
    }

    public static final List<LovPojo> getPeriod() {
        List<LovPojo> result = new ArrayList<>();
        result.add(new LovPojo("01", "01"));
        result.add(new LovPojo("15", "15"));
        return result;
    }

    public static final List<LovPojo> getMonths() {
        LovPojo obj;
        List<LovPojo> result = new ArrayList<>();
        for(int i = 1; i < 13; i++) {
            String month = StringUtils.padLeft(String.valueOf(i),2,'0');
            obj = new LovPojo(month, month);
            result.add(obj);
        }
        return result;
    }

    public static final List<LovPojo> getYears() {
        LovPojo obj;
        List<LovPojo> result = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = 0; i < 4; i++) {
            obj = new LovPojo(String.valueOf(currentYear + i), String.valueOf(currentYear + i));
            result.add(obj);
        }
        return result;
    }

    public static final List<LovPojo> getWbsType() {
        LovPojo obj;
        List<LovPojo> result = new ArrayList<>();
        for(WbsType type: WbsType.values()) {
            obj = new LovPojo(type.name(), type.name());
            result.add(obj);
        }
        return result;
    }

    public static final List<LovPojo> getStatus() {
        LovPojo obj;
        List<LovPojo> result = new ArrayList<>();
        for(Status type: Status.values()) {
            obj = new LovPojo(type.name(), type.name());
            result.add(obj);
        }
        return result;
    }

    public static final List<LovPojo> getCalendarStatus() {
        LovPojo obj;
        List<LovPojo> result = new ArrayList<>();
        for(CalenderStatus type: CalenderStatus.values()) {
            obj = new LovPojo(type.name(), type.name());
            result.add(obj);
        }
        return result;
    }
}
