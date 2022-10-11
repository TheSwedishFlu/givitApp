package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;

public class AccountController {


    @Controller
    public class ItemController {

        Logger logger = LoggerFactory.getLogger(AccountController.class);

        @Autowired
        private DataSource dataSource;



        @GetMapping("/myAccount")
        String myAccount(){
            logger.info("myAccount is running");
            return "myAccount";

        }

    }
}