package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GivitApplicationTests {

	@Autowired
	AccountRepository accountRepository;

	@Test
	public void testDescription() {
		Company givit = new Company("Givit", 5);
		Assertions.assertEquals("Givit is a company with 5 members.", givit.description());
	}

	@Test
	public void addAccountToDataBase(){
		Account test= new Account();
		test.setCity("Stockholm");
		test.setEmail("test@mail.com");
		test.setOrgnr(1111);
		test.setName("Testare");
		test.setPassword("123");
		accountRepository.save(test);
		Assertions.assertEquals("test@mail.com",accountRepository.findByEmail("test@mail.com").getEmail());
	}


}
