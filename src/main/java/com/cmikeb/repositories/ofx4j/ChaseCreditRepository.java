package com.cmikeb.repositories.ofx4j;

import net.sf.ofx4j.OFXException;
import net.sf.ofx4j.client.*;
import net.sf.ofx4j.client.impl.FinancialInstitutionServiceImpl;
import net.sf.ofx4j.client.impl.LocalResourceFIDataStore;
import net.sf.ofx4j.domain.data.banking.BankAccountDetails;
import net.sf.ofx4j.domain.data.common.Transaction;
import net.sf.ofx4j.domain.data.creditcard.CreditCardAccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Created by cmbylund on 11/22/15.
 */
@Service
public class ChaseCreditRepository {

    private static final String PWORD = "";
    private static final String UNAME = "cmikeb01";
    private static final String CC_FREEDOM = "4266841270686775";


    @Autowired
    private ResourceLoader resourceLoader;

    public List<Transaction> getTransactionsBetween(Date startDate, Date endDate) {

        try {
            Resource resource = resourceLoader.getResource("classpath:ofx/institutions.xml");

            File institutions = resource.getFile();
            FinancialInstitutionData data = new LocalResourceFIDataStore(institutions).getInstitutionData("636");
            FinancialInstitutionService service = new FinancialInstitutionServiceImpl();
            FinancialInstitution financialInstitution = service.getFinancialInstitution(data);

            BankAccountDetails bankAccountDetails = new BankAccountDetails();

            CreditCardAccountDetails creditCardAccountDetails = new CreditCardAccountDetails();
            creditCardAccountDetails.setAccountNumber(CC_FREEDOM);
            CreditCardAccount creditCardAccount = financialInstitution.loadCreditCardAccount(creditCardAccountDetails, UNAME, PWORD);

            AccountStatement accountStatement = creditCardAccount.readStatement(startDate, endDate);

            return accountStatement.getTransactionList().getTransactions();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (OFXException e) {
            e.printStackTrace();
        }

        return null;
    }

}
