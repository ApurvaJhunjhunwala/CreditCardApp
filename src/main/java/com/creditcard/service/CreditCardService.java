package com.creditcard.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.creditcard.exception.RecordNotFoundException;
import com.creditcard.model.CreditCard;
import com.creditcard.repo.CreditCardRepository;
import com.creditcard.validator.LuhnAlgorithmVAlidation;

@Service
public class CreditCardService {
	
	@Autowired
	CreditCardRepository creditCardRepository;
	
	public void addCard(CreditCard card) throws ValidationException {
		Boolean isValidCardNumber=LuhnAlgorithmVAlidation.isValidCreditCardNumber(String.valueOf(card.getCardNumber()));
		if(isValidCardNumber) {
			creditCardRepository.save(card);
		}else {
			throw new ValidationException("Invalid Card Number not compliant");
		}
		
		
	}
	
	public List<CreditCard> getAllCreditCards() throws RecordNotFoundException{
		List <CreditCard> cards = new ArrayList<CreditCard>();
		creditCardRepository.findAll().forEach(card->cards.add(card));
		return cards;
	}

}
