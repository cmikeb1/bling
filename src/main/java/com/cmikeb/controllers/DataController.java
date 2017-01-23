package com.cmikeb.controllers;

import com.cmikeb.models.dao.PeriodSnapshot;
import com.cmikeb.models.dao.TransactionDAO;
import com.cmikeb.models.domain.Category;
import com.cmikeb.models.domain.Constants;
import com.cmikeb.models.domain.Transaction;
import com.cmikeb.service.AirtableRepoService;
import com.cmikeb.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cmbylund on 10/25/15.
 */
@RestController
@RequestMapping("api")
public class DataController {
    @Autowired
    private AirtableRepoService airtableRepoService;
    @Autowired
    private DataService dataService;

    @RequestMapping("transaction")
    public List<Transaction> getAllTransactions() {
        return airtableRepoService.getTransactionRepo().getAllTransactions();
    }

    @RequestMapping(value = "transaction", method = RequestMethod.POST)
    public Transaction createTransaction(@RequestBody TransactionDAO transactionDAO) {
        return dataService.createTransaction(transactionDAO);
    }

    @RequestMapping("category")
    public List<Category> getAllCategories() {
        return airtableRepoService.getCategoryRepo().fetchAllCategories();
    }

    @RequestMapping("constants")
    public Constants getConstants() {
        return airtableRepoService.getConstantsRepo().fetchConstants();
    }

    @RequestMapping("snapshot")
    public PeriodSnapshot getCurrentPeriodSnapshot() {
        return dataService.getCurrentPeriodSnapshot();
    }

}
