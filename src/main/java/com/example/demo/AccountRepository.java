package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.List;

@Service
public interface AccountRepository extends CrudRepository<Account,Integer> {
    List<Account> findAll();
}
