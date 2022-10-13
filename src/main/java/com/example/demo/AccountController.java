package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;



    @Controller
    public class AccountController {

        Logger logger = LoggerFactory.getLogger(AccountController.class);

        @Autowired
        private DataSource dataSource;



        @GetMapping("/myAccount")
        String myAccount(){
            logger.info("myAccount is running");
            return "myAccount";
        }

        @GetMapping("/admin")
        String admin(){
            logger.info("admin is running");
            return "admin";
        }

        @GetMapping("/logoutuser")
        public String logout(HttpSession session, HttpServletResponse res)
        {
            session.removeAttribute("siteuser");
            return "redirect:/";
        }


    }
