package com.cmikeb.controllers;

import com.cmikeb.repositories.ofx4j.ChaseCreditRepository;
import com.cmikeb.service.TransactionsFromBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cmbylund on 10/24/15.
 */
@Controller
@Component
public class HomeController {

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/pending")
    public String pendingTransactions(Model model) {
        return "pending";
    }

}
