package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;


@Controller
public class ItemController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DataSource dataSource;
    @GetMapping("/")
    String start() {
        logger.info("start is running");
        return "startpage";
    }

    @GetMapping("/iteam")
    String itempage(){
        logger.info("itempage is running");
        return "iteamPage";
    }
    @GetMapping("/iteamCreat")
    String iteamCreat(){
        logger.info("iteamCreat is running");
        return "createIteam";
    }
    @GetMapping("/iteamDetails")
    String iteamDetails(){
        logger.info("iteamDetails is running");
        return "itemDetails";
    }

    @GetMapping("/givitTeam")
    String givitTeam(){
        logger.info("givitTeam is running");
        return "givitTeam";
    }

}
