package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.util.List;


@Controller
public class ItemController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/")
    String start() {
        logger.info("start is running");
        return "startpage";
    }

    @GetMapping("/items")
    String itemPage(Model model){
        logger.info("itemPage is running");
        List<Item> items=itemRepository.findAll();
        System.out.println(items.size());
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
    @GetMapping("/itemDetails")
    String itemDetails(){
        logger.info("itemDetails is running");
        return "itemDetails";
    }

    @GetMapping("/givitTeam")
    String givitTeam(){
        logger.info("givitTeam is running");
        return "givitTeam";
    }

    @GetMapping("/registerUser")
    String registerUser(){
        logger.info("registerUser is running");
        return "registerUser";
    }

}
