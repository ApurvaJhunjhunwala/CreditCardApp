package com.creditcard.demotest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.creditcard.demo.CreditCardApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CreditCardApplication.class)
@AutoConfigureMockMvc
public class CreditCardApplicationTest {
	
	@Test
	public void contextLoads() {
	}

}
