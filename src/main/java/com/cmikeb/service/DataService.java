package com.cmikeb.service;

import com.cmikeb.models.dao.PeriodSnapshot;
import com.cmikeb.models.dao.TransactionDAO;
import com.cmikeb.models.domain.Category;
import com.cmikeb.models.domain.Constants;
import com.cmikeb.models.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by cmbylund on 10/25/15.
 */
@Service
public class DataService {

    private static final long MS_IN_DAY = 86400000;

    @Autowired
    private AirtableRepoService airtableRepoService;


    public PeriodSnapshot getCurrentPeriodSnapshot() {
        // get data from Airtable
        List<Transaction> transactions = airtableRepoService.getTransactionRepo().getAllTransactions();
        List<Category> categories = airtableRepoService.getCategoryRepo().fetchAllCategories();
        Constants constants = airtableRepoService.getConstantsRepo().fetchConstants();

        // calculate current period from constants
        Date now = new Date();
        Date periodStartDay = calcPeriodDate("start", constants, now);
        Date periodEndDay = calcPeriodDate("end", constants, now);

        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().after(periodStartDay) && transaction.getTransactionDate().before(periodEndDay)) {
                for (Category category : categories) {
                    if (transaction.getCategoryId().equals(category.getId())) {
                        category.addTransaction(transaction);
                    }
                }
            }
        }

        PeriodSnapshot periodSnapshot = new PeriodSnapshot();
        periodSnapshot.setCategories(categories);
        periodSnapshot.setStartDay(periodStartDay);
        periodSnapshot.setEndDay(periodEndDay);
        periodSnapshot.setNow(now);

        return periodSnapshot;
    }

    private Date calcPeriodDate(String startOrEnd, Constants constants, Date now) {
        if (new Date().before(constants.getBaseDate())) {
            throw new IllegalArgumentException("Invalid base date");
        }

        Date periodStart = (Date) constants.getBaseDate().clone();
        Date periodEnd = new Date(periodStart.getTime() + constants.getPeriodLenghtDays() * MS_IN_DAY);

        while (periodEnd.before(now)) {
            periodStart = periodEnd;
            periodEnd = new Date(periodStart.getTime() + constants.getPeriodLenghtDays() * MS_IN_DAY);
        }

        if (startOrEnd.equals("start")) {
            return periodStart;
        } else
            return periodEnd;
    }

    public Transaction createTransaction(TransactionDAO transactionDAO) {
        return airtableRepoService.getTransactionRepo().createTransaction(transactionDAO);
    }

}
