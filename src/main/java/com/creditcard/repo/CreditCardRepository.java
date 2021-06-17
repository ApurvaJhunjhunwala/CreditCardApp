package com.creditcard.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.creditcard.model.CreditCard;

@Repository("cardRepository")
@Component
public interface CreditCardRepository extends CrudRepository<CreditCard, Integer>{

	

}
