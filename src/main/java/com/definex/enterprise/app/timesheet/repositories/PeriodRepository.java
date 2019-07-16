package com.definex.enterprise.app.timesheet.repositories;

import com.definex.enterprise.app.timesheet.entities.Period;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodRepository extends CrudRepository<Period, Long> {

    Period findByCode (String code);
}
