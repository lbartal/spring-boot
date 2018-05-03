package sample.data.jpa.integration.component;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import sample.data.jpa.domain.*;
import sample.data.jpa.service.QuoteClient;
import sample.data.jpa.service.ReviewService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class ReviewTest extends ComponentTestBase {

	private static String REVIEW_PATH = "/reviews/hotel/{hotelId}";

	@MockBean
	@Autowired
	QuoteClient quoteClient;

	@Autowired
	ReviewService reviewService;

	@Test
	public void retrieveReviewsForHotelWithNoReviewReturnsEmptyList(){
		MockMvcResponse actualResponse = given().get(REVIEW_PATH, 1);
		actualResponse.then()
				.statusCode(HttpStatus.OK.value());
		List<ReviewResponse> actualReviews = Arrays.asList(actualResponse.as(ReviewResponse[].class));
		assertThat(actualReviews.size(), is(0));
	}

	@Test
	public void retrieveReviewsForHotelWithReviewsReturnsReviewList(){
		Long hotelId = 2L;
		Quote randomQuote = new Quote("success", 13, "Mocked Random Quote");
		Mockito.doReturn(randomQuote).when(quoteClient).getRandomQuote();

		MockMvcResponse actualResponse = given().get(REVIEW_PATH, hotelId);
		actualResponse.then()
				.statusCode(HttpStatus.OK.value());
		List<ReviewResponse> actualReviews = Arrays.asList(actualResponse.as(ReviewResponse[].class));
		assertThat(actualReviews.size(), is(4));

		List<ReviewResponse> expectedReviews = reviewService.findByHotelId(hotelId);

		assertThat(actualReviews, sameBeanAs(expectedReviews)
				.ignoring(containsString("reviews"))
				.ignoring(containsString("checkInDate"))
				.ignoring(containsString("id")));
	}

	@Test
	public void addReviewsWithQuoteSavedAndReturnsAccepted(){
		Long hotelId = 1L;
		ReviewDetails reviewDetails = new ReviewDetails();
		reviewDetails.setCheckInDate(new Date());
		reviewDetails.setDetails("This is my own test summary as hotel review");
		reviewDetails.setQuoteId("3");
		reviewDetails.setRating(Rating.AVERAGE);
		reviewDetails.setTitle("Test Title");
		reviewDetails.setTripType(TripType.BUSINESS);

		MockMvcResponse actualResponse = given().contentType(ContentType.JSON).body(reviewDetails).post(REVIEW_PATH, hotelId);
		actualResponse.prettyPrint();
		actualResponse.then()
				.statusCode(HttpStatus.ACCEPTED.value());

		List<ReviewResponse> reviewList = reviewService.findByHotelId(hotelId);
		assertThat(reviewList.size(),  is(1));
		ReviewResponse reviewResponse = reviewList.get(0);
		ReviewDetails actualReviewDetails = extractReviewDetails(reviewResponse);
		assertThat(actualReviewDetails, sameBeanAs(reviewDetails));
	}

	@Test
	public void addReviewsWithoutQuoteSavedAndReturnsAccepted(){
		Long hotelId = 1L;
		ReviewDetails reviewDetails = new ReviewDetails();
		reviewDetails.setCheckInDate(new Date());
		reviewDetails.setDetails("This is my own test summary as hotel review");
		reviewDetails.setRating(Rating.AVERAGE);
		reviewDetails.setTitle("Test Title");
		reviewDetails.setTripType(TripType.BUSINESS);

		MockMvcResponse actualResponse = given().contentType(ContentType.JSON).body(reviewDetails).post(REVIEW_PATH, hotelId);
		actualResponse.prettyPrint();
		actualResponse.then()
				.statusCode(HttpStatus.ACCEPTED.value());

		List<ReviewResponse> reviewList = reviewService.findByHotelId(hotelId);
		assertThat(reviewList.size(),  is(1));
		ReviewResponse reviewResponse = reviewList.get(0);
		ReviewDetails actualReviewDetails = extractReviewDetails(reviewResponse);
		assertThat(actualReviewDetails, sameBeanAs(reviewDetails));
	}

}
