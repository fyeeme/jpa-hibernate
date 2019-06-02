package com.flyme.app.entity;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class  Period{

    private Date startDate;

    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Period [endDate=" + endDate + ", startDate=" + startDate + "]";
    }
}