package com.definex.enterprise.app.timesheet.repositories;

import com.definex.enterprise.app.timesheet.entities.Wbs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WbsRepository extends CrudRepository<Wbs, Long> {

    Wbs findByCode (String code);
}
