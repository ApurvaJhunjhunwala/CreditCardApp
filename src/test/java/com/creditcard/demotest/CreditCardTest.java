package com.creditcard.demotest;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.creditcard.demotest.*;
import com.creditcard.model.CreditCard;
import com.creditcard.repo.CreditCardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
public class CreditCardTest extends CreditCardApplicationTest{
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	
	 @MockBean
	 private CreditCardRepository mockRepository;
	 
	 @Autowired
	    private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
    public void find_allCards_OK() throws Exception {

        List<CreditCard> cards = Arrays.asList(
                new CreditCard("12345678903555", "TestApurva",100),
                new CreditCard( "12345678403555","TestApurva1",200));

        when(mockRepository.findAll()).thenReturn(cards);

        mockMvc.perform(get("/api/v1/getAllCards"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
               // .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cardNumber", is("12345678903555")))
                .andExpect(jsonPath("$[0].nameOnCard", is("TestApurva")))
                .andExpect(jsonPath("$[0].balance", is(100)))
                .andExpect(jsonPath("$[1].cardNumber", is("12345678403555")))
                .andExpect(jsonPath("$[1].nameOnCard", is("TestApurva1")))
                .andExpect(jsonPath("$[1].balance", is(200)));

        verify(mockRepository, times(1)).findAll();
    }
	 @Test
	    public void saveCard() throws Exception {

	        CreditCard addCard = new CreditCard( "12345678403555", "TestApurva", 100);
	        when(mockRepository.save(any(CreditCard.class))).thenReturn(addCard);

	        String expected = om.writeValueAsString(addCard);

	        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/addCard", addCard, String.class);

	        assertEquals(HttpStatus.CREATED, response.getStatusCode());
	        JSONAssert.assertEquals(expected, response.getBody(), false);

	        verify(mockRepository, times(1)).save(any(CreditCard.class));

	    }
	
	 @Test
	    public void find_nologin_401() throws Exception {

	        String expected = "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Unauthorized\",\"path\":\"/api/v1/addCard/\"}";

	        ResponseEntity<String> response = restTemplate
	                .getForEntity("/api/v1/addCard", String.class);

	        printJSON(response);

	        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
	        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

	        JSONAssert.assertEquals(expected, response.getBody(), false);

	    }

	    private static void printJSON(Object object) {
	        String result;
	        try {
	            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	            System.out.println(result);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
	    }
	    @Test
	    public void save_invalidCardNumber_400() throws JSONException {

	        String cardInJson = "{\"cardNumber\":\" 767767767676767\", \"nameOnCard\":\"abc\",\"balance\":\"12\"}";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(cardInJson, headers);

	        //Try exchange
	        ResponseEntity<String> response = restTemplate.exchange("/api/v1/addCard", HttpMethod.POST, entity, String.class);

	        String expectedJson = "{\"status\":400,\"errors\":[\"Card is not allowed.\"]}";
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

	        verify(mockRepository, times(0)).save(any(CreditCard.class));

	    }
	    @Test
	    public void save_cardNumber_400() throws JSONException {

	        String cardInJson = "{\"name\":\"ABC\"}";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(cardInJson, headers);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/addCard", entity, String.class);
	        //printJSON(response);

	        String expectedJson = "{\"status\":400,\"errors\":[\"Card is not allowed.\",\"Please provide a valid Card Number\"]}";
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

	        verify(mockRepository, times(0)).save.(CreditCard.class);

	    }
}
