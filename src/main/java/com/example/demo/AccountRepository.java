package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface AccountRepository extends CrudRepository<Account,Integer> {
}
