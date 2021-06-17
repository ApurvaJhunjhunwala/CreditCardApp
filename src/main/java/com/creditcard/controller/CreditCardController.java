package com.creditcard.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creditcard.exception.RecordNotFoundException;
import com.creditcard.exception.UnauthorizedException;
import com.creditcard.model.CreditCard;
import com.creditcard.service.CreditCardService;
import com.creditcard.validator.CreditCardValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
public class CreditCardController {
	
	@Autowired
	CreditCardService creditCardService;
	
	
	
	@PostMapping(value = "/addCard/")
	@ApiOperation(value = "Add Credit Card", notes = "Returns the 200 with card number")
	@ApiResponses(value = { 
			@ApiResponse(code = 500, message = "internal server error") })
	public ResponseEntity addCard(
			@RequestHeader(value="Authorization", required=false) String authToken,
			@Validated({CreditCardValidator.class }) @Valid @RequestBody  CreditCard card)
					throws UnauthorizedException, ValidationException, javax.xml.bind.ValidationException {
			creditCardService.addCard(card);
		return new ResponseEntity(HttpStatus.CREATED);

		
	}
	
	@GetMapping(value="/getAllCards")
	private ResponseEntity<List<CreditCard>> getAllStudents() throws RecordNotFoundException{
		return new ResponseEntity<List<CreditCard>>(creditCardService.getAllCreditCards(),HttpStatus.OK);
		
	}

}
