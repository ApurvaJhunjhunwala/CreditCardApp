package com.creditcard.demotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.creditcard.model.CreditCard;
import com.creditcard.repo.CreditCardRepository;
import com.creditcard.service.CreditCardService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreditCardServiceTest extends CreditCardApplicationTest {

	private MockMvc mockMvc;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@InjectMocks
	CreditCardService cardService;

	@MockBean
	private CreditCardRepository cardRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	static ObjectMapper om = new ObjectMapper();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
	 * @Test public void testControllerAddCard() { MockHttpServletRequest request =
	 * new MockHttpServletRequest(); RequestContextHolder.setRequestAttributes(new
	 * ServletRequestAttributes(request));
	 * 
	 * when(cardRepository.save(any(CreditCard.class))).thenReturn(true);
	 * 
	 * Employee employee = new Employee(1, "Lokesh", "Gupta",
	 * "howtodoinjava@gmail.com"); ResponseEntity<Object> responseEntity =
	 * employeeController.addEmployee(employee);
	 * 
	 * assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
	 * assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo(
	 * "/1"); }
	 */
	@Test
	public void testFindAll() throws Exception {
		// given
		List<CreditCard> cards = Arrays.asList(new CreditCard(1, "12345678903555", "TestApurva", 100),
				new CreditCard(2, "12345678403555", "TestApurva1", 200));

		when(cardRepository.findAll()).thenReturn(cards);

		mockMvc.perform(get("/api/v1/getAllCards")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				// .andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is("1"))).andExpect(jsonPath("$[0].cardNumber", is("12345678903555")))
				.andExpect(jsonPath("$[0].nameOnCard", is("TestApurva"))).andExpect(jsonPath("$[0].balance", is(100)))
				.andExpect(jsonPath("$[1].id", is("2"))).andExpect(jsonPath("$[1].cardNumber", is("12345678403555")))
				.andExpect(jsonPath("$[1].nameOnCard", is("TestApurva1"))).andExpect(jsonPath("$[1].balance", is(200)));

		verify(cardRepository, times(1)).findAll();
	}

	@Test
	public void testAddCard() {
		CreditCard card = new CreditCard(1, "12345678903555", "TestApurva", 100);
		ResponseEntity<String> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/api/v1/addCard", card, String.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
	}

}
