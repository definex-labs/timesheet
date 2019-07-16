package com.definex.enterprise.app.timesheet.services;

import com.definex.enterprise.app.timesheet.entities.*;
import com.definex.enterprise.app.timesheet.repositories.*;
import com.definex.enterprise.app.timesheet.utility.Consts;
import com.definex.enterprise.app.timesheet.utility.Utils;
import com.definex.enterprise.app.timesheet.view.LovPojo;
import com.definex.enterprise.app.timesheet.view.PeriodView;
import com.definex.enterprise.app.timesheet.view.TimeRowPojo;
import com.definex.enterprise.app.timesheet.view.TimeSheetView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TimeManagementService {

    private final TimeSheetRepository timeSheetRepository;

    private final PeriodRepository periodRepository;

    private final WbsRepository wbsRepository;

    private final CalendarInfoRepository calendarInfoRepository;

    private final PeriodStatusRepository periodStatusRepository;

    @Autowired
    public TimeManagementService(TimeSheetRepository timeSheetRepository,
                                 PeriodRepository periodRepository,
                                 WbsRepository wbsRepository,
                                 CalendarInfoRepository calendarInfoRepository,
                                 PeriodStatusRepository periodstatusRepository) {
        this.timeSheetRepository = timeSheetRepository;
        this.periodRepository = periodRepository;
        this.wbsRepository = wbsRepository;
        this.calendarInfoRepository = calendarInfoRepository;
        this.periodStatusRepository = periodstatusRepository;
    }

    public List<LovPojo> getAllWbs() {
        Iterator<Wbs> wbsIterator = wbsRepository.findAll().iterator();
        List<LovPojo> result = new ArrayList<>();
        while (wbsIterator.hasNext()) {
            Wbs entity = wbsIterator.next();
            result.add(new LovPojo(String.valueOf(entity.getId()), entity.getCode()));
        }
        return result;
    }

    public List<LovPojo> getAllPeriods(boolean withId) {
        Iterator<Period> periodIterator = periodRepository.findAll().iterator();
        List<LovPojo> result = new ArrayList<>();
        while (periodIterator.hasNext()) {
            Period entity = periodIterator.next();
            if (withId) {
                result.add(new LovPojo(String.valueOf(entity.getId()), entity.getCode()));
            } else {
                result.add(new LovPojo(String.valueOf(entity.getCode()), entity.getCode()));
            }
        }
        return result;
    }

    private PeriodStatus getPeriodStatus(Period period) {
        PeriodStatus periodStatus = new PeriodStatus();
        periodStatus.setPeriod(period);
        if (getActivePeriod().equals(period.getCode())) {
            periodStatus.setStatus(Consts.PeriodStatusType.READY);
        } else {
            periodStatus.setStatus(Consts.PeriodStatusType.OVERDUE);
        }

        return periodStatus;
    }

    public TimeSheetView getTimeSheetView(TimeSheetView timeSheetView) {

        //Period Status
        Period period = periodRepository.findByCode(timeSheetView.getPeriod());
        PeriodStatus periodStatus = periodStatusRepository.findByUserIdAndPeriod(Utils.getCurrentUserName(),period)
                                                                                .orElse(getPeriodStatus(period));

        timeSheetView.setPeriodStatus(periodStatus);

        //Get Calendar Details and fill style & rows
        List<CalendarInfo> calendarInfos = calendarInfoRepository.findAllByPeriodOrdered(timeSheetView.getPeriod());
        timeSheetView.setCalenderInfoList(calendarInfos);
        int dayIndex = 0;
        List<TimeRowPojo> rows = new ArrayList<>();
        for (CalendarInfo calendarInfo : calendarInfos) {
            timeSheetView.setDayStyle(dayIndex, calendarInfo.getStatus().name());
            List<TimeSheet> charges = timeSheetRepository.findByUserIdAndDate(Utils.getCurrentUserName(), calendarInfo.getDate());
            for (TimeSheet charge : charges) {
                boolean newRow = true;
                for(TimeRowPojo row : rows) {
                    if (charge.getChargeCode().getId().toString().equals(row.getChargeCode())) {
                        row.setHour(dayIndex, charge.getHour().intValue());
                        newRow = false;
                        break;
                    }
                }
                if (newRow) {
                    TimeRowPojo row = new TimeRowPojo();
                    row.setChargeCode(charge.getChargeCode().getId().toString());
                    row.setHour(dayIndex, charge.getHour().intValue());
                    rows.add(row);
                }
            }
            dayIndex++;
        }
        timeSheetView.setRows(rows);
        return timeSheetView;
    }

    public void savePeriod(PeriodView periodView) throws ParseException {
        Period period = new Period();
        period.setCode(periodView.getPeriod()+"/"+periodView.getMonth()+"/"+periodView.getYear());
        periodRepository.save(period);
        createCalenderInfo(period.getCode(),periodView.getMonth(), periodView.getYear());

    }

    public void deletePeriod(Long id) {

        Period period = periodRepository.findById(id).get();
        if (timeSheetRepository.findByPeriod(period).size() > 0) { //There are time reports submitted for this period
            return;
        }
        List<CalendarInfo> calendarInfos = calendarInfoRepository.findAllByPeriodOrdered(period.getCode());
        for (CalendarInfo info : calendarInfos) {
            calendarInfoRepository.delete(info);
        }
        periodRepository.delete(period);
    }

    private void createCalenderInfo(String period, String month, String year) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String dt = year + month;
        int endIndex = 16;
        if("01".equals(period.substring(0,2))) {
            dt += "01";
            endIndex = 15;
        } else {
            dt += "16";
        }

        Calendar c = Calendar.getInstance();
        c.setTime(df.parse(dt));
        for (int i = 0; i < endIndex; i++) {
            CalendarInfo calenderInfo = new CalendarInfo();
            calenderInfo.setDate(c.getTime());
            calenderInfo.setCreationDate(Calendar.getInstance().getTime());
            calenderInfo.setPeriod(period);
            if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                    || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {  //or sunday
                calenderInfo.setStatus(Consts.CalenderStatus.weekend);
            } else {
                calenderInfo.setStatus(Consts.CalenderStatus.regular);
            }
            calendarInfoRepository.save(calenderInfo);
            c.add(Calendar.DATE, 1);
            if (c.get(Calendar.MONTH) +1 != Integer.parseInt(month)) {
                break;
            }
        }
    }

    public String getActivePeriod() {
        return calendarInfoRepository.findByDate(new Date()).getPeriod();
    }

    public void saveTimeSheet(TimeSheetView view) {
        List<CalendarInfo> calendarInfos = calendarInfoRepository.findAllByPeriodOrdered(view.getPeriod());
        if (validateTime(view, calendarInfos)) {
            Period period = periodRepository.findByCode(view.getPeriod());
            PeriodStatus periodStatus = periodStatusRepository.findByUserIdAndPeriod(Utils.getCurrentUserName(),period)
                    .orElse(getPeriodStatus(period));
            int dayIndex = 0;
            for (CalendarInfo calendarInfo : calendarInfos) {
                for (TimeRowPojo row : view.getRows()) {
                    int hour = row.getHours()[dayIndex];
                    if (hour != 0) {
                        Wbs wbs = wbsRepository.findById(Long.valueOf(row.getChargeCode())).get();
                        TimeSheet time = getTimeSheet(wbs, calendarInfo.getDate());
                        if (Consts.PeriodStatusType.PROCESSED.equals(periodStatus.getStatus())) {
                            time.setAdjustmentHour(Long.valueOf(hour));
                            time.setAdjustmentDate(new Date());
                        } else {
                            time.setHour(Long.valueOf(hour));
                            time.setPeriod(period);
                        }
                        timeSheetRepository.save(time);
                    }
                }
                dayIndex++;
            }
            if (periodStatus.getStatus().equals(Consts.PeriodStatusType.PROCESSED)) {
                periodStatus.setStatus(Consts.PeriodStatusType.ADJUSTED);
            } else {
                periodStatus.setStatus(Consts.PeriodStatusType.SUBMITTED);
                periodStatus.setPeriod(period);
            }

            periodStatusRepository.save(periodStatus);
        }
    }

    private TimeSheet getTimeSheet(Wbs wbs, Date date) {
        TimeSheet timeSheet = timeSheetRepository.findByUserIdAndDateAndChargeCode(Utils.getCurrentUserName(), date, wbs);
        if (timeSheet == null) {
            timeSheet = new TimeSheet();
            timeSheet.setChargeCode(wbs);
            timeSheet.setDate(date);
        }
        return timeSheet;
    }

    public boolean validateTime(TimeSheetView view, List<CalendarInfo> calendarInfos) {
        boolean validationResult = true;
        int dayIndex = 0;
        int dailyTotal = 0;
        for (TimeRowPojo row : view.getRows()) {
            int total = 0;
            for (int hour : row.getHours()) {
                total += hour;
            }
            if (total == 0 && view.getRows().size() > 1) {
                view.getRows().remove(row);
            }
        }

        for (CalendarInfo calendarInfo : calendarInfos) {
            if (calendarInfo.getStatus().equals(Consts.CalenderStatus.regular)) {
                for (TimeRowPojo row : view.getRows()) {
                    dailyTotal += row.getHours()[dayIndex];
                }
                if (dailyTotal < 8) {
                    validationResult = false;
                    break;
                }
            }
            dayIndex++;
        }

        return validationResult;
    }
}
