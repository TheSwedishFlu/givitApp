package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemRepository extends CrudRepository<Item,Integer> {

    List<Item> findAll();

    Item findById(int id);



}
