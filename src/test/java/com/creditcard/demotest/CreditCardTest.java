package com.creditcard.demotest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.creditcard.model.CreditCard;
import com.creditcard.repo.CreditCardRepository;
import com.creditcard.service.CreditCardService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreditCardServiceTest extends CreditCardApplicationTest {



	@InjectMocks
	CreditCardService cardService;

	@MockBean
	private CreditCardRepository cardRepository;


	static ObjectMapper om = new ObjectMapper();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testServiceGetAllCards() throws Exception {

		List<CreditCard> cards = Arrays.asList(new CreditCard(1, "12345678903555", "TestApurva", 100),
				new CreditCard(2, "12345678403555", "TestApurva1", 200));

		when(cardRepository.findAll()).thenReturn(cards);
		List<CreditCard> cardList = cardService.getAllCreditCards();
		assertEquals(2, cardList.size());

		verify(cardRepository, times(1)).findAll();
	}

	@Test
	public void testServiceSavecard() throws JSONException {
		CreditCard addCard = new CreditCard(1, "12345678903555", "TestApurva", 100);
		when(cardRepository.save(Mockito.any(CreditCard.class))).thenReturn(addCard);
		cardRepository.save(addCard);
		verify(cardRepository, times(1)).save(addCard);

	}
	

}
