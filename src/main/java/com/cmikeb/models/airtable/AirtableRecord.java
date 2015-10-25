package com.cmikeb.models.airtable;

import java.util.Date;
import java.util.Map;

/**
 * Created by cmbylund on 10/24/15.
 */
public class AirtableRecord {
    private String id;
    private Map<String, Object> fields;
    private Date createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "AirtableRecord{" +
                "id='" + id + '\'' +
                ", fields=" + fields +
                ", createdTime=" + createdTime +
                '}';
    }
}
