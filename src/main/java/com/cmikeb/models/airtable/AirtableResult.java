package com.cmikeb.models.airtable;

import java.util.List;

/**
 * Created by cmbylund on 10/24/15.
 */
public class AirtableResult {
    private List<AirtableRecord> records;

    public List<AirtableRecord> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableRecord> records) {
        this.records = records;
    }
}
