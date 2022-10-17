package com.example.demo;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemRepository extends CrudRepository<Item,Integer> {

    List<Item> findAll();

    Item findById(int id);

    List<Item> findByLocation(String location);
    List<Item> findByDeliveryType(String deliveryType);
    List<Item> findByDeliveryTypeAndLocation(String deliveryType, String location);

  //  List<Item> findByAccount(Account account, Sort sort);


}
