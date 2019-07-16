package com.definex.enterprise.app.timesheet.repositories;

import com.definex.enterprise.app.timesheet.entities.Period;
import com.definex.enterprise.app.timesheet.entities.PeriodStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodStatusRepository extends CrudRepository<PeriodStatus, Long> {
    Optional<PeriodStatus> findByUserIdAndPeriod(String userId, Period period);
}

