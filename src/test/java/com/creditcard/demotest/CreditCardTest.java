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
	/*
	 * @Test public void testAllEmployees() { ResponseEntity<List<CreditCard>>
	 * responseEntity;
	 * 
	 * responseEntity = this.restTemplate .getForObject("http://localhost:" + port +
	 * "/api/v1/getAllCards", ResponseEntity<>); assertEquals(200,
	 * responseEntity.getStatusCodeValue()); }
	 * 
	 * @Test public void testAddCard() { CreditCard card = new CreditCard(1,
	 * "12345678903555", "TestApurva", 100); ResponseEntity<String> responseEntity =
	 * this.restTemplate .postForEntity("http://localhost:" + port +
	 * "/api/v1/addCard", card, String.class); assertEquals(201,
	 * responseEntity.getStatusCodeValue()); }
	 */

}
