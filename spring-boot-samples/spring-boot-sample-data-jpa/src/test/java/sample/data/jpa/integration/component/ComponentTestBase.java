package sample.data.jpa.integration.component;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sample.data.jpa.SampleDataJpaApplication;
import sample.data.jpa.domain.ReviewDetails;
import sample.data.jpa.domain.ReviewResponse;
import sample.data.jpa.service.HotelRepository;

@Test
@SpringBootTest(classes = SampleDataJpaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration
public class ComponentTestBase extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private MockMvc mvc;
	@Autowired

	protected HotelRepository hotelRepository;

	@BeforeMethod
	public void setup() {
		RestAssuredMockMvc.mockMvc(mvc);
		MockitoAnnotations.initMocks(this);
	}

	public ReviewDetails extractReviewDetails(ReviewResponse reviewResponse){
		ReviewDetails actualReviewDetails = new ReviewDetails();
		actualReviewDetails.setCheckInDate(reviewResponse.getCheckInDate());
		actualReviewDetails.setDetails(reviewResponse.getDetails());
		if (reviewResponse.getQuoteId() != null)
			actualReviewDetails.setQuoteId(reviewResponse.getQuoteId());
		actualReviewDetails.setRating(reviewResponse.getRating());
		actualReviewDetails.setTitle(reviewResponse.getTitle());
		actualReviewDetails.setTripType(reviewResponse.getTripType());
		return actualReviewDetails;
	}
}
