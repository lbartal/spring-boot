/*
 * Copyright 2012-2018 the original author or authors.
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

package sample.data.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import sample.data.jpa.domain.City;
import sample.data.jpa.domain.HotelSummary;
import sample.data.jpa.domain.ReviewResponse;

import java.util.ArrayList;
import java.util.List;

@Component("reviewService")
@Transactional
class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;

	private final HotelRepository hotelRepository;

	public ReviewServiceImpl(ReviewRepository reviewRepository,
							 HotelRepository hotelRepository) {
		this.reviewRepository = reviewRepository;
		this.hotelRepository = hotelRepository;
	}


	@Override
	public List<ReviewResponse> findByHotelId(long hotelId) {
		List<ReviewResponse> reviewList = new ArrayList<>();
		reviewRepository.findByHotelId(hotelId).stream().forEach( review -> {
				ReviewResponse response = new ReviewResponse();
				response.setCheckInDate(review.getCheckInDate());
				response.setDetails(review.getDetails());
				response.setHotel(review.getHotel());
				response.setIndex(review.getIndex());
				response.setQuoteId(review.getQuoteId());
				response.setRating(review.getRating());
				response.setTitle(review.getTitle());
				response.setTripType(review.getTripType());
				response.setId(review.getId());
				response.setQuote(new QuoteClient().getRandomQuote());
				reviewList.add(response);
			}
		);
		return reviewList;
	}


}
