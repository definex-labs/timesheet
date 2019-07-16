package com.definex.enterprise.app.timesheet.repositories;

import com.definex.enterprise.app.timesheet.entities.Period;
import com.definex.enterprise.app.timesheet.entities.TimeSheet;
import com.definex.enterprise.app.timesheet.entities.Wbs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSheetRepository extends CrudRepository<TimeSheet, Long> {

    List<TimeSheet> findByUserId(String userId);

    List<TimeSheet> findByPeriod (Period period);

    List<TimeSheet> findByUserIdAndDate(String userId, Date date);

    TimeSheet findByUserIdAndDateAndChargeCode(String userId, Date date, Wbs chargeCode);
}
