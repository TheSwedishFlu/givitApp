package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class ItemController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    AccountRepository accountRepository;
    @GetMapping("/")
    String start() {
        logger.info("start is running");
        return "startpage";
    }

    @GetMapping("/items")
    String itemPage(Model model){
        logger.info("itemPage is running");
        List<Item> items=itemRepository.findAll();
        model.addAttribute("items", items);
        /*Item item=new Item("Stolar","Nya stolar som vi vill ge bort pga omplanering av kontor.", "Möbel", "Stockholm",50,"Pick-up","givit.png");
        model.addAttribute("item", item);*/
        return "itemPage";
    }

   /* @PostMapping("/items")
    String itemsPage(@ModelAttribute List<Item> items, Model model){

        return "itemPage";
    }*/

    @GetMapping("/createItem")
    String itemCreate(){
        logger.info("itemCreate is running");
        return "createItem";
    }
    @GetMapping("/itemDetails/{Id}")
    String itemDetails(@PathVariable int Id, Model model){
        Item item = itemRepository.findById(Id);
        model.addAttribute("item", item);
        logger.info("itemDetails is running");
        return "itemDetails";
    }

    @GetMapping("/givitTeam")
    String givitTeam(){
        logger.info("givitTeam is running");

        return "givitTeam";
    }

    @GetMapping("/registerUser")
    String registerUser(Model model){
        model.addAttribute("newAccount",new Account());
        logger.info("registerUser is running");
        return "registerUser";
    }
    @PostMapping("/save")
    public String set(@ModelAttribute Account account) {
        System.out.println("save is running");
        accountRepository.save(account);
        System.out.println("saved it");
        return "redirect:/";
    }

}
