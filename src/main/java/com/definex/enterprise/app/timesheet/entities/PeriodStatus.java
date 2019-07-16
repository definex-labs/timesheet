package com.definex.enterprise.app.timesheet.entities;

import com.definex.enterprise.app.timesheet.utility.Consts;
import com.definex.enterprise.app.timesheet.utility.Utils;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PeriodStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    @ManyToOne
    private Period period;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Consts.PeriodStatusType status;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updateDate;

    @PrePersist
    void onPrePersist() {
        this.setUpdateDate(new Date());
        this.setUserId(Utils.getCurrentUserName());
    }

    @PreUpdate
    void onPerUpdate() {
        this.setUpdateDate(new Date());
        this.setUserId(Utils.getCurrentUserName());
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

    public Consts.PeriodStatusType getStatus() {
        return status;
    }

    public void setStatus(Consts.PeriodStatusType status) {
        this.status = status;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
