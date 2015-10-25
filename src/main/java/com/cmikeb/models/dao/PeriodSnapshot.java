package com.cmikeb.models.dao;

import com.cmikeb.models.domain.Category;

import java.util.Date;
import java.util.List;

/**
 * Created by cmbylund on 10/25/15.
 */
public class PeriodSnapshot {
    private List<Category> categories;
    private Date startDay;
    private Date endDay;
    private Date now;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }
}
