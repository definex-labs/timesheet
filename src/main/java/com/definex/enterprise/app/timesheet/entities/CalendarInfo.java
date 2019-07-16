package com.definex.enterprise.app.timesheet.entities;

import com.definex.enterprise.app.timesheet.utility.Consts;
import com.definex.enterprise.app.timesheet.utility.Utils;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery(name = "CalendarInfo.findAllByPeriodOrdered",
        query = "SELECT c FROM CalendarInfo c WHERE c.period = ?1 ORDER BY c.date ASC")
public class CalendarInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String period;

    @Temporal(value = TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Consts.CalenderStatus status;

    private String createdBy;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;

    private String updatedBy;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updateDate;

    @PrePersist
    void onPrePersist() {
        this.setCreationDate(new Date());
        this.setCreatedBy(Utils.getCurrentUserName());
    }

    @PreUpdate
    void onPerUpdate() {
        this.setUpdateDate(new Date());
        this.setUpdatedBy(Utils.getCurrentUserName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Consts.CalenderStatus getStatus() {
        return status;
    }

    public void setStatus(Consts.CalenderStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
