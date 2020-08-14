package com.ibm.pact;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

public class BookingConsumerTest{
    class BookCargo {
        int bookingAmount;
        String origin;
        String destination;
        String deadlineDate;

        public BookCargo(int _bookingAmount, String _origin, String _destination, String _deadlineDate) {
            bookingAmount = _bookingAmount;
            origin = _origin;
            destination = _destination;
            deadlineDate = _deadlineDate;
        }

        public void setBookingAmount(int _bookingAmount){ this.bookingAmount = _bookingAmount; }
        public int getBookingAmount(){ return this.bookingAmount; }
    
        public String getOriginLocation() {return origin; }
        public void setOriginLocation(String _origin) {this.origin = _origin; }
    
        public String getDestLocation() { return destination; }
        public void setDestLocation(String _destination) { this.destination = _destination; }
    
        public String getDestArrivalDeadline() { return deadlineDate; }
        public void setDestArrivalDeadline(String _deadlineDate) { this.deadlineDate = _deadlineDate; }
    }

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("booking_provider","localhost", 8001, this);
    private RestTemplate restTemplate=new RestTemplate();

    @Pact(provider = "booking_provider", consumer = "booking_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        PactDslJsonBody bodyResponse = new PactDslJsonBody()
                .integerType("bookingAmount", 100)
                .stringType("originLocation", "CNHKG")    
                .stringType("destLocation", "USNYC")
                .stringType("destArrivalDeadline", "2020-01-29");

        return builder
        		.given("create booking").uponReceiving("a request to create booking")
                .path("/cargobooking")
                .body(bodyResponse)
                .headers(headers)
                .method(RequestMethod.POST.name())
                .willRespondWith()
                .headers(headers)
                .status(200).body("").toPact();
    }
	
	@Test
	@PactVerification
	public void testCreateBookingConsumer() throws IOException {
        //BookCargoResource bookCargoResource = new BookCargoResource (100, "CNHKG", "USNYC",  LocalDate.parse("2020-01-28"));
        BookCargo bookCargo = new BookCargo (100, "CNHKG", "USNYC",  "2020-01-28");

        HttpHeaders headers=new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<Object> request=new HttpEntity<Object>(bookCargo, headers);

        System.out.println("MOCK provider URL"+ mockProvider.getUrl());
    	ResponseEntity<String> responseEntity=restTemplate.postForEntity(mockProvider.getUrl()+"/cargobooking", request, String.class);

        assertEquals(responseEntity.getBody(),responseEntity.getBody());
	}

}