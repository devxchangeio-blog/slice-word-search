package com.slice.word.search.config;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.slice.word.search.controller.WordSearchService;
import com.slice.word.search.dto.WordSearchDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SliceWordSearchApplication.class)
@WebAppConfiguration
public class SliceWordSearchApplicationTests {

	final RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	public WordSearchService wordSearchService;

	private final static String serviceUrl = "http://localhost:8085/wordsearch";

	@Test
	public void testApiWordSearchHi() throws JsonProcessingException {

		ResponseEntity<WordSearchDTO> response = restTemplate.getForEntity(serviceUrl + "/hi", WordSearchDTO.class);
		assertNotNull(response);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(71, is(response.getBody().getWordCount()));
		assertTrue(response.getBody().getCounter()>0);
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void tesApiWordSearchException() throws JsonProcessingException {

		restTemplate.getForEntity(serviceUrl, WordSearchDTO.class);
	}
	
	@Test
	public void tesWordSearchHi() throws JsonProcessingException {

		ResponseEntity<WordSearchDTO> response =wordSearchService.get("hi");
		
		assertNotNull(response);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(71, is(response.getBody().getWordCount()));
		assertTrue(response.getBody().getCounter()>0);
	}
	
	@Test
	public void tesWordSearchWithNullInput() throws JsonProcessingException {

		ResponseEntity<WordSearchDTO> response =wordSearchService.get(null);
		assertNotNull(response);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}
	
	@Test
	public void tesWordSearchWithEmptyString() throws JsonProcessingException {

		ResponseEntity<WordSearchDTO> response =wordSearchService.get("");
		assertNotNull(response);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}
	
	@Test
	public void testWordSearchWithNoOccurrence() throws JsonProcessingException {

		ResponseEntity<WordSearchDTO> response =wordSearchService.get("karthik");
		assertNotNull(response);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(0,is(response.getBody().getWordCount()));
		
	}

}
