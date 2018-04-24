package sample.data.jpa.service;

import org.springframework.web.client.RestTemplate;
import sample.data.jpa.domain.Quote;

import java.util.Arrays;
import java.util.List;

public class QuoteClient {

	private static String quoteApiUrl = "http://gturnquist-quoters.cfapps.io/api";

	public Quote getRandomQuote(){
		RestTemplate restTemplate = new RestTemplate();
		Quote quote = restTemplate.getForObject(quoteApiUrl + "/random", Quote.class);
		return quote;
	}

	public Quote getQuoteById(String id){
		RestTemplate restTemplate = new RestTemplate();
		Quote quote = restTemplate.getForObject(quoteApiUrl + "/" + id, Quote.class);
		return quote;
	}

	public List<Quote> getAllQuotes(){
		RestTemplate restTemplate = new RestTemplate();
		List<Quote> quotes = Arrays.asList(restTemplate.getForObject(quoteApiUrl + "/", Quote[].class));
		return quotes;
	}


}
