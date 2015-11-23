package com.cmikeb.repositories.airtable;

import com.cmikeb.models.domain.Transaction;
import com.cmikeb.models.airtable.AirtableRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmbylund on 10/25/15.
 */
@Service
public class AirtableTransactionRepository extends AirtableBaseRepository {


    private static final String TABLE_ID = "tbl4bwe48jDCTEQaT";

    @Override
    public String getTableId() {
        return TABLE_ID;
    }

    public List<Transaction> getAllTransactions() {
        List<AirtableRecord> airtableRecords = fetchAllRecords();
        List<Transaction> transactions = new ArrayList<>();
        for (AirtableRecord record : airtableRecords) {
            if (record.getFields().get("Name") != null) {
                transactions.add(mapTransaction(record));
            }
        }
        return transactions;
    }

    private Transaction mapTransaction(AirtableRecord record) {
        Transaction transaction = new Transaction();
        transaction.setId(record.getId());
        transaction.setCreatedDate(record.getCreatedTime());
        transaction.setName((String) record.getFields().get("Name"));
        transaction.setAmount((Double) record.getFields().get("Amount"));

        String dateString = (String) record.getFields().get("Date");
        transaction.setTransactionDate(parseDate(dateString));

        Object categoriesObject = record.getFields().get("Category");
        List categories = (List) categoriesObject;

        transaction.setCategoryId((String) categories.get(0));
        return transaction;
    }
}
