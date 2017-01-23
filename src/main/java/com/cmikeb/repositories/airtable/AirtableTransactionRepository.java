package com.cmikeb.repositories.airtable;

import com.cmikeb.models.airtable.AirtableCreateRecord;
import com.cmikeb.models.airtable.AirtableResult;
import com.cmikeb.models.dao.TransactionDAO;
import com.cmikeb.models.domain.Transaction;
import com.cmikeb.models.airtable.AirtableRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cmbylund on 10/25/15.
 */
@Service
public class AirtableTransactionRepository extends AirtableBaseRepository {

    @Value("${airtable.tables.transactions}")
    private String tableId;

    @Override
    public String getTableId() {
        return tableId;
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
        transaction.setAmount(Double.parseDouble(record.getFields().get("Amount").toString()));

        String dateString = (String) record.getFields().get("Date");
        transaction.setTransactionDate(parseDate(dateString));

        Object categoriesObject = record.getFields().get("Category");
        List categories = (List) categoriesObject;

        transaction.setCategoryId((String) categories.get(0));
        return transaction;
    }

    public Transaction createTransaction(TransactionDAO transaction) {

        AirtableCreateRecord transactionRecord = new AirtableCreateRecord();
        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("Name", transaction.getName());
        transactionMap.put("Amount", transaction.getAmount());
        transactionMap.put("Category", Arrays.asList(transaction.getCategory().getId()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
        transactionMap.put("Date", simpleDateFormat.format(transaction.getDate()));
        transactionRecord.setFields(transactionMap);

        return mapTransaction(postNewRecord(transactionRecord));
    }
}
