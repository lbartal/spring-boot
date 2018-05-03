package sample.data.jpa.integration.component;

import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import sample.data.jpa.domain.City;
import sample.data.jpa.domain.Hotel;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class HotelsTest extends ComponentTestBase {

	private static String HOTELS_PATH = "/hotels";

	@Test
	public void retrieveAllHotels() throws Exception {
		MockMvcResponse actualResponse = given().get(HOTELS_PATH);
		actualResponse.then()
				.statusCode(HttpStatus.OK.value());
		List<Hotel> actualHotels = Arrays.asList(actualResponse.as(Hotel[].class));
		assertThat(actualHotels.size(), is(27));
		List<Hotel> expectedHotels = hotelRepository.findAll();
		assertThat(actualHotels, sameBeanAs(expectedHotels)
				.ignoring(containsString("reviews"))
				.ignoring(City.class));
	}
}
