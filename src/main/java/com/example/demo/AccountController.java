package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    AccountRepository accountRepository;
    @GetMapping("/myAccount")
    String myAccount(Model model) {
        model.addAttribute("editAcc",new Account());
        return "myAccount";
    }
    @PostMapping("/myAccount")
    String myAccountSave(HttpServletRequest req, HttpServletResponse res ,Model model, @ModelAttribute Account editAcc) {

        HttpSession session = req.getSession();
        String acc = (String) session.getAttribute("account");
        Account overRideAcc=accountRepository.findByEmail(acc);
        model.addAttribute("editAcc", editAcc);

        List<Item> itemList= itemRepository.findAll();
        for (Item item : itemList) {
            if (item.getOrgnr()==overRideAcc.getOrgnr()){
                item.setOrgnr(editAcc.getOrgnr());
                System.out.println("bytt orgNr fran: "+overRideAcc.getOrgnr()+"_till: "+item.getOrgnr());
            }
        }

        accountRepository.save(editAcc);
        accountRepository.delete(overRideAcc);
        System.out.println("acc saved");
        session.setAttribute("Username",editAcc.getEmail());
        session.setAttribute("account",editAcc.getEmail());

        return "myAccount";
    }

    @GetMapping("/admin")
    String admin() {
        logger.info("admin is running");
        return "admin";
    }

    @GetMapping("/logoutuser")
    public String logout(HttpSession session, HttpServletResponse res) {
        session.removeAttribute("Username");
        return "redirect:/";
    }


}
