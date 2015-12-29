package com.cmikeb.models.airtable;

import java.util.Date;
import java.util.Map;

/**
 * Created by cmbylund on 12/29/15.
 */
public class AirtableCreateRecord {
    private Map<String, Object> fields;

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "AirtableRecord{" +
                ", fields=" + fields +
                '}';
    }
}
