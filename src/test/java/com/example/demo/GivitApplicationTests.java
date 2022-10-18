package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GivitApplicationTests {

	@Test
	public void testDescription() {
		Company givit = new Company("Givit", 5);
		Assertions.assertEquals("Givit is a company with 5 members.", givit.description());
	}


}
