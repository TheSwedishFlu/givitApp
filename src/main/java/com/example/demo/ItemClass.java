package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.ItemController.UPLOAD_DIRECTORY;

public class ItemClass {

    @Autowired
    private DataSource dataSource;

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    AccountRepository accountRepository;

    public String itemsPage(@RequestParam(required =false) String city, @RequestParam(required =false) String delivery , Model model){

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



    public String processBuy(@PathVariable int id, @RequestParam String addItem, Model model, HttpSession session, HttpServletRequest currentSession){
        Item buyItem=itemRepository.findById(id);
        System.out.println("Buy item with id: " + id);
        List<Item> addedItems=new ArrayList<>();
        addedItems.add(buyItem);
        session.setAttribute("shoppingList",addedItems);
        model.addAttribute("session",session);

        return "redirect:/cart";
    }


    public String displayUploadForm(Model model) {
        model.addAttribute("Item",new Item());
        return "createItem";
    }



    public String uploadImage(Model model, @ModelAttribute Item item, @RequestParam("fileimage") MultipartFile file) throws IOException {
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
