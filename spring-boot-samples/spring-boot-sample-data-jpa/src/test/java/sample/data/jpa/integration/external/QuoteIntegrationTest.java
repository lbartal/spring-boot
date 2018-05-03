package sample.data.jpa.integration.external;

import org.hamcrest.MatcherAssert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import sample.data.jpa.domain.Quote;
import sample.data.jpa.service.QuoteClient;

import java.util.*;

public class QuoteIntegrationTest {

	@Test(enabled = true, description = "this should be statistical based check, can fail now at a low %")
	public void getRandomQuoteShouldReturnRandomQuotes() {
		QuoteClient quoteClient = new QuoteClient();

		List<Quote> quoteList = new ArrayList<>();
		for (int i = 0; i<10 ;i++){
			quoteList.add(quoteClient.getRandomQuote());
		}

		Set<Quote> quoteSet = new HashSet<Quote>();
		quoteSet.addAll(quoteList);
		System.out.println(quoteSet.size());
		assertThat(quoteSet.size(), greaterThan(1));
	}

	@Test
	public void getQuoteByIdShouldReturnCorrectQuote() {

		Quote expectedQuote = new Quote("success", 5, "Spring Boot solves this problem. It gets rid of XML and wires up common components for me, so I don't have to spend hours scratching my head just to figure out how it's all pieced together.");
		QuoteClient quoteClient = new QuoteClient();
		Quote quote = quoteClient.getQuoteById("5");
		assertThat(quote, is(expectedQuote));
	}

	@Test
	public void getAllQuotesShouldReturnList() {
		QuoteClient quoteClient = new QuoteClient();
		List<Quote> quotes = quoteClient.getAllQuotes();
		assertThat(quotes.size(), is(12));
	}
}