package com.definex.enterprise.app.timesheet.repositories;

import com.definex.enterprise.app.timesheet.entities.CalendarInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CalendarInfoRepository extends CrudRepository<CalendarInfo, Long> {

    List<CalendarInfo> findAllByPeriodOrdered(String period);

    CalendarInfo findByDate(Date date);
}
