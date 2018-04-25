/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.data.jpa.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sample.data.jpa.domain.*;
import sample.data.jpa.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Controller
public class SampleController {

	@Autowired
	private CityService cityService;

	@Autowired
	private HotelService hotelService;


	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ReviewRepository reviewRepository;


	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/cities")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<City> getCities() {
		//return this.cityService.getCity("Bath", "UK");
		return cityRepository.findAll();
	}

	@GetMapping("/hotels")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<Hotel> getHotels() {
		return hotelRepository.findAll();
	}

	@GetMapping("/reviews/hotel/{hotelId}")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<ReviewResponse> getReviews(@PathVariable("hotelId") long id ) {
		return reviewService.findByHotelId(id);
	}

	@PostMapping("/reviews/hotel/{hotelId}")
	@ResponseBody
	@Transactional
	public ResponseEntity addReview(@PathVariable("hotelId") long id, @RequestBody ReviewDetails reviewDetails) {
		hotelService.addReview(
				hotelRepository.findById(id),
				reviewDetails);
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}

}
