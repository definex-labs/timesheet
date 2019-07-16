package com.definex.enterprise.app.timesheet.entities;

import com.definex.enterprise.app.timesheet.utility.Utils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Code is mandatory")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
