package com.definex.enterprise.app.timesheet.entities;

import com.definex.enterprise.app.timesheet.utility.Consts;
import com.definex.enterprise.app.timesheet.utility.Utils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
public class Wbs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Code is mandatory")
    private String code;

    @NotEmpty(message = "Description is mandatory")
    private String description;

    @NotEmpty(message = "Owner is mandatory")
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Consts.WbsType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Consts.Status status;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Consts.AuthRequired authRequired;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Consts.WbsType getType() {
        return type;
    }

    public void setType(Consts.WbsType type) {
        this.type = type;
    }

    public Consts.Status getStatus() {
        return status;
    }

    public void setStatus(Consts.Status status) {
        this.status = status;
    }

    public Consts.AuthRequired getAuthRequired() {
        return authRequired;
    }

    public void setAuthRequired(Consts.AuthRequired authRequired) {
        this.authRequired = authRequired;
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
