package com.cmikeb.service;

import com.cmikeb.repositories.airtable.AirtableCategoryRepository;
import com.cmikeb.repositories.airtable.AirtableConstantsRepository;
import com.cmikeb.repositories.airtable.AirtableTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cmbylund on 10/25/15.
 */
@Service
public class AirtableRepoService {
    @Autowired
    private AirtableTransactionRepository transactionRepo;
    @Autowired
    private AirtableConstantsRepository constantsRepo;
    @Autowired
    private AirtableCategoryRepository categoryRepo;

    public AirtableTransactionRepository getTransactionRepo() {
        return transactionRepo;
    }

    public AirtableConstantsRepository getConstantsRepo() {
        return constantsRepo;
    }

    public AirtableCategoryRepository getCategoryRepo() {
        return categoryRepo;
    }
}
