package com.cmikeb.controllers;

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

}
