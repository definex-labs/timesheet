package com.definex.enterprise.app.timesheet.entities;

import com.definex.enterprise.app.timesheet.utility.Utils;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
public class TimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    @ManyToOne
    private Period period;

    @Temporal(value = TemporalType.DATE)
    private Date date;

    private Long hour;

    @ManyToOne
    private Wbs chargeCode;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date actualSubmissionDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date adjustmentDate;

    private Long adjustmentHour;

    @PrePersist
    void onPrePersist() {
        this.setActualSubmissionDate(new Date());
        this.setUserId(Utils.getCurrentUserName());
    }

    @PreUpdate
    void onPerUpdate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }

    public Wbs getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(Wbs chargeCode) {
        this.chargeCode = chargeCode;
    }

    public Date getActualSubmissionDate() {
        return actualSubmissionDate;
    }

    public void setActualSubmissionDate(Date actualSubmissionDate) {
        this.actualSubmissionDate = actualSubmissionDate;
    }

    public Date getAdjustmentDate() {
        return adjustmentDate;
    }

    public void setAdjustmentDate(Date adjustmentDate) {
        this.adjustmentDate = adjustmentDate;
    }

    public Long getAdjustmentHour() {
        return adjustmentHour;
    }

    public void setAdjustmentHour(Long adjustmentHour) {
        this.adjustmentHour = adjustmentHour;
    }
}
