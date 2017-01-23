package com.cmikeb.repositories.airtable;

import com.cmikeb.models.domain.Constants;
import com.cmikeb.models.airtable.AirtableRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cmbylund on 10/25/15.
 */
@Service
public class AirtableConstantsRepository extends AirtableBaseRepository {
    @Value("${airtable.tables.constants}")
    private String tableId;

    @Override
    String getTableId() {
        return tableId;
    }

    public Constants fetchConstants(){
        List<AirtableRecord> airtableRecords = fetchAllRecords();
        Constants constants = new Constants();
        for (AirtableRecord record : airtableRecords) {
            String id = (String) record.getFields().get("Identifier");
            switch (id){
                case "base_period":
                    String dateString = (String) record.getFields().get("DateValue");
                    constants.setBaseDate(parseDate(dateString));
                    break;
                case "period_length_days":
                    constants.setPeriodLenghtDays((int) record.getFields().get("NumberValue"));
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Could not match constant with identifier %s", id));
            }
        }
        return constants;
    }
}
