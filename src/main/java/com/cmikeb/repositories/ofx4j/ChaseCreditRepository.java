package com.cmikeb.repositories.ofx4j;

import net.sf.ofx4j.OFXException;
import net.sf.ofx4j.client.*;
import net.sf.ofx4j.client.impl.BaseFinancialInstitutionData;
import net.sf.ofx4j.client.impl.FinancialInstitutionServiceImpl;
import net.sf.ofx4j.client.impl.LocalResourceFIDataStore;
import net.sf.ofx4j.domain.data.banking.BankAccountDetails;
import net.sf.ofx4j.domain.data.common.Transaction;
import net.sf.ofx4j.domain.data.creditcard.CreditCardAccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;


/**
 * Created by cmbylund on 11/22/15.
 */
@Service
public class ChaseCreditRepository {

    @Value("${access.chase.password}")
    private String PWORD;
    private static final String UNAME = "cmikeb01";
    private static final String CC_FREEDOM = "4266841270686775";


    @Autowired
    private ResourceLoader resourceLoader;

    public List<Transaction> getTransactionsBetween(Date startDate, Date endDate) {

        try {
            Resource resource = resourceLoader.getResource("classpath:ofx/institutions.xml");

            BaseFinancialInstitutionData chaseData = new BaseFinancialInstitutionData();
            chaseData.setId("636");
            chaseData.setFinancialInstitutionId("10898");
            chaseData.setName("JPMorgan Chase Bank (credit cards)");
            chaseData.setOFXURL(new URL("https://ofx.chase.com"));
            chaseData.setOrganization("B1");

            FinancialInstitutionService service = new FinancialInstitutionServiceImpl();
            FinancialInstitution financialInstitution = service.getFinancialInstitution(chaseData);


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
