package com.creditcard.model;

import java.math.BigInteger;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.creditcard.validator.CreditCardValidator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class CreditCard {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
//	@Size(min = 10, max = 19, groups = CreditCardValidator.CreateCardValidation.class)
	//@Pattern(regexp = "(^[0-9]{19})")
	private BigInteger cardNumber;
	private String nameOnCard;
	private Integer balance=0;
	
	
	public CreditCard(String cardnumber,String name, Integer balance) {
		this.cardNumber=new BigInteger(cardnumber);
		this.balance=balance;
		this.nameOnCard=name;
		
		
	}
	
	 public  long generateRandom(int prefix) {
	        Random rand = new Random();

	        long x = (long)(rand.nextDouble()*10000000);

	        String s = String.valueOf(prefix) + String.format("%014d", x);
	        return Long.valueOf(s);
	    }

	public CreditCard() {
		super();
	}
}
