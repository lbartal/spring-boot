package sample.data.jpa.integration.end2end;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import sample.data.jpa.domain.*;
import sample.data.jpa.integration.component.ComponentTestBase;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;

public class End2EndTest extends ComponentTestBase {

	private static String HOTELS_PATH = "/hotels";
	private static String REVIEW_PATH = "/reviews/hotel/{hotelId}";

	@Test
	public void reviewJourney(){
		//Get list of hotels
		MockMvcResponse actualResponse = given().get(HOTELS_PATH);
		actualResponse.then()
				.statusCode(HttpStatus.OK.value());
		List<Hotel> actualHotels = Arrays.asList(actualResponse.as(Hotel[].class));
		assertThat(actualHotels.size(), is(27));

		//Select Hotel 1 for writing review
		Long hotelId = 1L;

		//Write review
		ReviewDetails reviewDetails = new ReviewDetails();
		reviewDetails.setCheckInDate(new Date());
		reviewDetails.setDetails("This is my own test summary as hotel review");
		reviewDetails.setQuoteId("3");
		reviewDetails.setRating(Rating.AVERAGE);
		reviewDetails.setTitle("Test Title");
		reviewDetails.setTripType(TripType.BUSINESS);

		actualResponse = given().contentType(ContentType.JSON).body(reviewDetails).post(REVIEW_PATH, hotelId);
		actualResponse.then()
				.statusCode(HttpStatus.ACCEPTED.value());

		//Retrieve the fresh written review
		actualResponse = given().get(REVIEW_PATH, hotelId);
		actualResponse.then()
				.statusCode(HttpStatus.OK.value());
		List<ReviewResponse> actualReviews = Arrays.asList(actualResponse.as(ReviewResponse[].class));
		assertThat(actualReviews.size(), is(1));
		ReviewDetails actualReviewDetails = extractReviewDetails(actualReviews.get(0));
		assertThat(actualReviewDetails, sameBeanAs(reviewDetails));
		assertThat(actualReviews.get(0).getQuoteId(), is(String.valueOf(actualReviews.get(0).getQuote().getValue().getId())));
	}
}
