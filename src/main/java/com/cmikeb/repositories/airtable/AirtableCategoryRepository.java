package com.cmikeb.repositories.airtable;

import com.cmikeb.models.domain.Category;
import com.cmikeb.models.airtable.AirtableRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmbylund on 10/25/15.
 */
@Service
public class AirtableCategoryRepository extends AirtableBaseRepository {

    @Value("${airtable.tables.categories}")
    private String tableId;

    @Override
    public String getTableId() {
        return tableId;
    }

    public List<Category> fetchAllCategories(){
        List<AirtableRecord> airtableRecords = fetchAllRecords();
        List<Category> categories = new ArrayList<>();
        for (AirtableRecord record : airtableRecords) {
            categories.add(mapCategory(record));
        }
        return categories;
    }

    private Category mapCategory(AirtableRecord record) {
        Category category = new Category();

        category.setId(record.getId());
        category.setCreatedDate(record.getCreatedTime());
        category.setName((String) record.getFields().get("Name"));
        try {
            category.setBudget((Double) record.getFields().get("Budget"));
        } catch (ClassCastException ex) {
            category.setBudget((double) (Integer) record.getFields().get("Budget"));
        }
        category.setNotes((String) record.getFields().get("Notes"));
        List transactionIds = (List) record.getFields().get("Transactions");
        category.setTransactionIds( (List<String>) transactionIds);

        return category;
    }
}
