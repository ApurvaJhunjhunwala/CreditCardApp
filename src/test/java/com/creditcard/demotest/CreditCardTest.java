package com.creditcard.demotest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.creditcard.exception.RecordNotFoundException;
import com.creditcard.model.CreditCard;
import com.creditcard.repo.CreditCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
public class CreditCardTest extends CreditCardApplicationTest{
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	
	 @MockBean
	 private CreditCardRepository mockRepository;
	 
	 
	 static ObjectMapper  om = new ObjectMapper();

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
    public void find_allCards_OK() throws Exception {

        List<CreditCard> cards = Arrays.asList(
                new CreditCard(1L,"12345678903555", "TestApurva",100),
                new CreditCard(2L, "12345678403555","TestApurva1",200));

        when(mockRepository.findAll()).thenReturn(cards);

        mockMvc.perform(get("/api/v1/getAllCards"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
               // .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1L")))
                .andExpect(jsonPath("$[0].cardNumber", is("12345678903555")))
                .andExpect(jsonPath("$[0].nameOnCard", is("TestApurva")))
                .andExpect(jsonPath("$[0].balance", is(100)))
                .andExpect(jsonPath("$[1].id", is("2L")))
                .andExpect(jsonPath("$[1].cardNumber", is("12345678403555")))
                .andExpect(jsonPath("$[1].nameOnCard", is("TestApurva1")))
                .andExpect(jsonPath("$[1].balance", is(200)));

        verify(mockRepository, times(1)).findAll();
    }
	@Test
    public void find_allCards_NotFound() throws Exception {

        List<CreditCard> cards = Arrays.asList(
                new CreditCard(1L,"12345678903555", "TestApurva",100),
                new CreditCard(2L, "12345678403555","TestApurva1",200));

        when(mockRepository.findAll()).thenThrow(new RecordNotFoundException("No Records Found"));

        mockMvc.perform(get("/api/v1/getAllCards"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
               

        verify(mockRepository, times(1)).findAll();
    }
	
	    @Test
	    public void save_invalidCardNumber_400() throws JSONException {

	    	 CreditCard addCard = new CreditCard( 1L,"12132113213", "TestApurva", 100);
		        when(mockRepository.save(Mockito.any(CreditCard.class))).thenThrow(new ValidationException());
	        verify(mockRepository, times(1)).save(Mockito.any(CreditCard.class));

	    }
	   

	    @Test
		 public void savecard() throws JSONException {
		    	 CreditCard addCard = new CreditCard( 1L,"12345678903555", "TestApurva", 100);
			        when(mockRepository.save(Mockito.any(CreditCard.class))).thenReturn(addCard);
		        verify(mockRepository, times(1)).save(Mockito.any(CreditCard.class));

		    }
		
}
