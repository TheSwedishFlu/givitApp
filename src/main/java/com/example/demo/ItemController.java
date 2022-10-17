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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class ItemController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private DataSource dataSource;

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

    @PostMapping("/items")
    String itemsPage(@RequestParam(required =false) String city,@RequestParam(required =false) String delivery , Model model){

        System.out.println(city);
        System.out.println(delivery);

        if(!city.equals("City") && delivery != null){
            List<Item> items=itemRepository.findByDeliveryTypeAndLocation(delivery,city);
            System.out.println("Both values "+items.size());
            model.addAttribute("items", items);

        } else if( delivery != null || city.equals("City")){
                List<Item> items=itemRepository.findByDeliveryType(delivery);
            System.out.println("deliver value "+items.size());
            model.addAttribute("items", items);
        } else if(city != null ){
            List<Item> items= itemRepository.findByLocation(city);
            System.out.println("city value "+items.size());
            model.addAttribute("items", items);
        }
        return "itemPage";
    }

    @GetMapping("/itemDetails/{Id}")
    String itemDetails(@PathVariable int Id, Model model){
        Item item = itemRepository.findById(Id);
        model.addAttribute("item", item);
        logger.info("itemDetails is running");
        return "itemDetails";
    }

    @PostMapping("/itemDetails/{id}")
    String processBuy(@PathVariable int id, @RequestParam String addItem, Model model, HttpSession session, HttpServletRequest currentSession){
        Item buyItem=itemRepository.findById(id);
        System.out.println("Buy item with id: " + id);
        List<Item> addedItems=new ArrayList<>();
        addedItems.add(buyItem);
        session.setAttribute("shoppingList",addedItems);
        model.addAttribute("session",session);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    String cart(Model model, HttpSession session, HttpServletRequest currentSession){
        logger.info("Cart is running");
        currentSession.getSession();
        List<Item> addedItems= (List<Item>) session.getAttribute("shoppingList");
        for (Item i : addedItems){
            Item removeFromItems=itemRepository.findById(i.getId());
            itemRepository.delete(removeFromItems);
            System.out.println("Item removed from repository:"+ removeFromItems.getName());
        }
        //session.setAttribute("shoppingList",addedItems);
        //model.addAttribute("session",session);
        return "cart";
    }

    @PostMapping("/cart")
    String cartEdits(@RequestParam int remove, Model model, HttpSession session, HttpServletRequest currentSession){
        currentSession.getSession();
        List<Item> addedItems= (List<Item>) session.getAttribute("shoppingList");
            Item removedFromShoppingList=itemRepository.findById(remove);
            itemRepository.save(removedFromShoppingList);
        System.out.println("Item added to repository:");
        for (Item i:addedItems) {
            if (i.getId()==removedFromShoppingList.getId()){
                addedItems.remove(i);
                System.out.println("removed from cart:" +addedItems.remove(i));

            }
        }
        session.setAttribute("shoppingList",addedItems);
        model.addAttribute("session",session);

        return "cart";
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

    @PostMapping("/login")
    public String Login(HttpSession session, @RequestParam String Username, @RequestParam String Password){

        Account account = accountRepository.findByEmail(Username);

        if (account!= null && account.getPassword().equals(Password)) {
            session.setAttribute("Username", Username);
            return "redirect:/items";
        }

        return "redirect:/";
    }

    @GetMapping("/create")
    String createItem(Model model){
        model.addAttribute("Item",new Item());
        return "createItem";
    }
    @PostMapping("/si")
    public String saveItem(@ModelAttribute Item item) {
        System.out.println("save is running");
        itemRepository.save(item);
        return "redirect:/";
    }


//tobbe start
public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static";

    @GetMapping("/createItem")
    public String displayUploadForm(Model model) {
        model.addAttribute("Item",new Item());
        return "createItem";
    }

    @PostMapping("/saveItem")
    public String uploadImage(Model model,@ModelAttribute Item item, @RequestParam("fileimage") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        //model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        System.out.println(fileNames);
        itemRepository.save(item);
        return "redirect:/";
    }

}
