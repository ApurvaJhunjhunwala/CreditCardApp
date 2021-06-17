package com.creditcard.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "com.creditcard.repo" ,"com.creditcard.model",
		"com.creditcard.service", "com.creditcard.controller", "com.creditcard.security",
		"com.creditcard.validator", "com.creditcard.exception","com.creditcard.demotest" ,
		})
 
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages="com.creditcard.repo")
@EnableTransactionManagement
@EntityScan(basePackages="com.creditcard.model")
public class CreditCardApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CreditCardApplication.class, args);
	}

}
