package com.cmikeb.models.domain;

import java.util.Date;

/**
 * Created by cmbylund on 10/25/15.
 */
public class Constants {
    private Date baseDate;
    private int periodLenghtDays;

    public Date getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    public int getPeriodLenghtDays() {
        return periodLenghtDays;
    }

    public void setPeriodLenghtDays(int periodLenghtDays) {
        this.periodLenghtDays = periodLenghtDays;
    }
}
