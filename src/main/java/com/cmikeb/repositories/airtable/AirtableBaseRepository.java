package com.cmikeb.repositories.airtable;

import com.cmikeb.models.airtable.AirtableRecord;
import com.cmikeb.models.airtable.AirtableResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by cmbylund on 10/25/15.
 */
public abstract class AirtableBaseRepository {

    protected static final Logger log = LoggerFactory.getLogger(AirtableTransactionRepository.class);

    protected static final String AIRTABLE_URI = "https://api.airtable.com/v0/app6KKb1Hzsn3GWtl/";

    @Value("${airtable.api-key}")
    protected String airtableApiKey;

    abstract String getTableId();

    protected List<AirtableRecord> fetchAllRecords() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + airtableApiKey);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        log.info(String.format("Fetching airbase records for table %s", getTableId()));
        HttpEntity<AirtableResult> result = restTemplate.exchange(AIRTABLE_URI + getTableId(), HttpMethod.GET, httpEntity, AirtableResult.class);
        log.info(String.format("Found %d records for table %s", result.getBody().getRecords().size(), getTableId()));
        return result.getBody().getRecords();
    }

    protected Date parseDate(String dateString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            return df.parse(dateString);
        } catch (Exception ex) {
            log.error("Could not parse transaction date " + dateString, ex);
            return null;
        }
    }
}
