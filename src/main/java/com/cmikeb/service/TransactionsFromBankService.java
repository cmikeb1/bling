package com.cmikeb.service;

import com.cmikeb.models.domain.Transaction;
import com.cmikeb.repositories.ofx4j.ChaseCreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cmbylund on 11/22/15.
 */
@Service
public class TransactionsFromBankService {

    private static final long MS_IN_DAY = 60000 * 60 * 24;

    @Autowired
    private ChaseCreditRepository chaseCreditRepository;

    public List<Transaction> getTransactionsLast7Days() {
        Date startDate = new Date(new Date().getTime() - MS_IN_DAY * 7);
        Date endDate = new Date();

        List<net.sf.ofx4j.domain.data.common.Transaction> sourceTransactions = chaseCreditRepository.getTransactionsBetween(startDate, endDate);

        List<Transaction> transactions = new ArrayList<>();
        sourceTransactions.stream().forEach(t -> transactions.add(new Transaction(
                t.getId(),
                t.getDatePosted(),
                t.getDatePosted(),
                null,
                null,
                t.getName(),
                t.getAmount()
        )));

        return transactions;
    }
}
