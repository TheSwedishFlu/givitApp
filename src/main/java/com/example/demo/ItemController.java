package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.*;
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
        items.sort(Comparator.comparing(Item::getName));
        model.addAttribute("items", items);
        /*Item item=new Item("Stolar","Nya stolar som vi vill ge bort pga omplanering av kontor.", "Möbel", "Stockholm",50,"Pick-up","givit.png");
        model.addAttribute("item", item);*/
        return "itemPage";
    }

    @PostMapping("/items")
    String itemsPage(@RequestParam(required =false) String city,@RequestParam(required =false) String delivery ,@RequestParam(required =false) String clear, Model model, HttpSession session){

        System.out.println(city);
        System.out.println(delivery);
        if (clear!=null){
            delivery=null;
            city="City";
            session.setAttribute("delivery",delivery);
            session.setAttribute("city",city);
            List<Item> items= itemRepository.findAll();
            model.addAttribute("items", items);
            return "itemPage";
        }
        if(!city.equals("City")){
            session.setAttribute("city",city);
        }
        if(!city.equals("City") && delivery != null){
            List<Item> items=itemRepository.findByDeliveryTypeAndLocation(delivery,city);
            System.out.println("Both values "+items.size());
            session.setAttribute("delivery",delivery);
            session.setAttribute("city",city);
            model.addAttribute("items", items);

        } else if( delivery != null || city.equals("City")){
            List<Item> items=itemRepository.findByDeliveryType(delivery);
            System.out.println("deliver value "+items.size());
            session.setAttribute("delivery",delivery);
            session.setAttribute("city",city);
            model.addAttribute("items", items);
        } else if(city != null ){
            List<Item> items= itemRepository.findByLocation(city);
            System.out.println("city value "+items.size());
            session.setAttribute("delivery",delivery);
            session.setAttribute("city",city);
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
        List<Item> addedItems= (List<Item>) session.getAttribute("shoppingList");
        if(addedItems==null){
            addedItems=new ArrayList<>();
        }
        addedItems.add(buyItem);
        session.setAttribute("shoppingList",addedItems);
        model.addAttribute("session",session);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    String cart(Model model, HttpSession session, HttpServletRequest currentSession){
        logger.info("Cart is running");

        try {
            currentSession.getSession();
            List<Item> addedItems= (List<Item>) session.getAttribute("shoppingList");
            for (Item i : addedItems){
                Item removeFromItems=itemRepository.findById(i.getId());
                if (removeFromItems!=null){
                    itemRepository.delete(removeFromItems);
                    System.out.println("Item removed from repository:"+ removeFromItems.getName());
                }
            }
        }catch (Exception e){
            System.out.println("Empty cart");
        }

        //session.setAttribute("shoppingList",addedItems);
        //model.addAttribute("session",session);
        return "cart";
    }

    @PostMapping("/cart")
    String cartEdits(@RequestParam int remove, Model model, HttpSession session, HttpServletRequest currentSession){
        currentSession.getSession();
        Item foundItem=null;
        List<Item> addedItems= (List<Item>) session.getAttribute("shoppingList");
        for (Item item: addedItems){
            if (item.getId()==remove){
                foundItem=item;
                itemRepository.save(item);
            }
        }
        if(foundItem!=null){
            addedItems.remove(foundItem);
        }
            //Item removedFromShoppingList=itemRepository.findById(remove);
        System.out.println("Item added to repository:" +foundItem.getName());


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
    public String Login(HttpServletRequest req, HttpServletResponse res , @RequestParam String Username,
                        @RequestParam String Password)throws ServletException {

        HttpSession session = req.getSession();
        session.setAttribute("account", Username);

        Account account = accountRepository.findByEmail(Username);

        if (account!= null && account.getPassword().equals(Password)) {
            session.setAttribute("Username", Username);
            return "redirect:/items";
        }
        return "redirect:/";
    }

public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\";

    @GetMapping("/createItem")
    public String displayUploadForm(Model model) {
        model.addAttribute("Item",new Item());
        return "createItem";
    }

    @PostMapping("/saveItem")
    public String uploadImage(HttpServletRequest req, HttpServletResponse res ,Model model, @ModelAttribute Item item, @RequestParam("fileimage")
                                  MultipartFile file) throws ServletException, IOException {

        HttpSession session = req.getSession();
        try {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            //model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
            System.out.println(fileNames);

            model.addAttribute("item",item);
            if (!fileNames.toString().equals("img/givit.png")){
                String name=fileNames.toString();
                item.setImage("img/"+name);
            }

        } catch (Exception e) {
            System.out.println("no image selected");
        }
        String acc =(String) session.getAttribute("account");
        System.out.println((String) session.getAttribute("account"));
        Account account = accountRepository.findByEmail(acc);

        item.setOrgnr(account.getOrgnr());
        System.out.println("this is it: "+account.getOrgnr());
        itemRepository.save(item);
        return "redirect:/items";
    }
    @GetMapping("/accItemList")
    String getAccItemList(Model model, HttpSession session, HttpServletRequest currentSession){

        String acc =(String) session.getAttribute("Username");
        List<Account> allAcc = accountRepository.findAll();
        List<Item> allItem = itemRepository.findAll();
        List<Item> addedItems = new ArrayList<>();

        Account theAcc= new Account();
        for (Account account : allAcc) {
            if (account.getEmail().equals(acc)){
                theAcc=account;
                System.out.println("the acc found is: "+theAcc);
            }
        }

        System.out.println("acc orgNr: "+theAcc.getOrgnr());
        for (Item item : allItem) {
            System.out.println(item.getOrgnr());
            if (theAcc.getOrgnr()== item.getOrgnr()){
                addedItems.add(item);
                System.out.println("added item: "+item);
            }
        }

        if(addedItems==null){
            addedItems=new ArrayList<>();
        }
        session.setAttribute("shoppingList",addedItems);
        model.addAttribute("session",session);

        System.out.println("itemList klar");
        return "accItemList";
    }

    @PostMapping("/accItemList")
    String postAccItemList(@RequestParam int remove, Model model, HttpSession session, HttpServletRequest currentSession) {

        currentSession.getSession();
            Item foundItem = null;
            List<Item> addedItems = (List<Item>) session.getAttribute("shoppingList");
            for (Item item : addedItems) {
                if (item.getId() == remove) {
                    foundItem = item;
                    //itemRepository.save(item);
                    itemRepository.delete(item);
                }
            }

            if (foundItem != null) {
                addedItems.remove(foundItem);
            }
            //Item removedFromShoppingList=itemRepository.findById(remove);
            System.out.println("Item added to repository:");

            return "accItemList";
        }
    }

//Test
