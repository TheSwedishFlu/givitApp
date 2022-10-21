package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GivitApplicationTests {

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	ItemRepository itemRepository;


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
	@Test
	public void DemoHasTwoItems(){
		int demoNr=0;
		List<Item> demoList=itemRepository.findAll();
		for (Item item : demoList) {
			if (item.getOrgnr()==122){
				demoNr++;
			}
		}
		Assertions.assertEquals(2,demoNr);
	}
	@Test
	public void ItemsHasUniqueID(){

		boolean itemIdUnique=false;
		List <Item> itemList=itemRepository.findAll();
		Item item1;

		for (Item item : itemList) {
			item1=item;

			for (Item item2 : itemList) {
				if (item1.getOrgnr()!=item2.getOrgnr()&&item1.getId()!=item2.getId()){
					itemIdUnique=true;
				}
			}
		}
		Assertions.assertEquals(true,itemIdUnique);
	}


}
