package no.arktekk.training.spring.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import junit.framework.Assert;
import no.arktekk.training.spring.auction.AuctionApp;
import no.arktekk.training.spring.domain.Album;
import no.arktekk.training.spring.domain.Auction;
import no.arktekk.training.spring.domain.AuctionList;
import no.arktekk.training.spring.domain.Category;
import no.arktekk.training.spring.domain.Label;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

public class RestJsonIntegrationTest {

	private static final String BASE_URI = "http://localhost:8080/restapi";
	private static AuctionApp auctionApp;
	private static RestTemplate restTemplate;
	private static ObjectMapper mapper;
	private static HttpEntity<byte[]> emptyJsonRequest;

	@BeforeClass
	public static void beforeClass() throws Exception {
		auctionApp = new AuctionApp();
		auctionApp.start();

		restTemplate = new RestTemplate();
		mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		emptyJsonRequest = new HttpEntity<byte[]>(headers);
	}

	@AfterClass
	public static void afterClass() throws Exception {
		auctionApp.stop();
	}

	/**
	 * Implement RestAuctionController.listAuctionsJson that handles
	 * "List auction" requests for clients that accepts "application/json".
	 * 
	 * Tips:
	 *  - accept-header for application/json
	 */
	@Test
	public void oppgave_1() throws Exception {
		ResponseEntity<String> response = restTemplate.exchange(BASE_URI
				+ "/auctions", HttpMethod.GET, emptyJsonRequest, String.class);

		System.out.println("ResponseBody: " + response.getBody());

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		AuctionList auctionList = unmarshallJson(response, AuctionList.class);
		Assert.assertEquals(2, auctionList.getAuctions().size());
	}

	/**
	 * Implement RestAuctionController.getAuctionJson that handles 
	 * "Get auction" requests for clients that accepts "application/json"
	 */
	@Test
	public void oppgave_2a() throws Exception {
		ResponseEntity<String> response = restTemplate.exchange(BASE_URI
				+ "/auctions/{auctionId}", HttpMethod.GET, emptyJsonRequest,
				String.class, 1);

		System.out.println("ResponseBody: " + response.getBody());

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		Auction auction = unmarshallJson(response, Auction.class);
		Assert.assertNotNull(auction);
		Assert.assertEquals("Mint prog rock albums", auction.getDescription());
	}

	/**
	 * Add logic to RestAuctionController.getAuctionJson so that HttpStatus.NOT_FOUND 
	 * is returned when GETing an auction that does not exist.
	 * 
	 * Tips:
	 *  - throw RestException when auction is not found
	 *  - define an exception-handler method in the Controller using @ExceptionHandler
	 */
	@Test
	public void oppgave_2b() throws Exception {

		try {
			restTemplate.exchange(BASE_URI + "/auctions/{auctionId}",
					HttpMethod.GET, emptyJsonRequest, String.class, 10);
			Assert.fail("Should throw exception because response status != 2xx.");
		} catch (HttpClientErrorException e) {
			System.out.println("ResponseBody: " + e.getResponseBodyAsString());

			Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
			Assert.assertEquals("Auction with id 10 does not exist.",
					e.getResponseBodyAsString());
		}

	}

	/**
	 * Implement RestAuctionController.createAuctionJson that handles 
	 * "Create auction" requests for clients that accepts "application/json"
	 */
	@Test
	public void oppgave_3() throws Exception {
		Album album = new Album(null, "TestAlbum", "TestArtist", new Category(101, "category"), new Label(102, "label"));
		Auction auction = new Auction(null, 100.0, "New auction", new DateTime(), new DateTime().plusDays(1), Lists.newArrayList(album));
		
		ResponseEntity<String> response = restTemplate.exchange(BASE_URI
				+ "/auctions", HttpMethod.POST, newJsonHttpEntity(marshalJson(auction)), String.class);

		System.out.println("ResponseBody: " + response.getBody());

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		Auction savedAuction = unmarshallJson(response, Auction.class);
		Assert.assertNotNull(savedAuction);
		Assert.assertEquals("New auction", savedAuction.getDescription());
	}

	private HttpEntity<byte[]> newJsonHttpEntity(byte[] body) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-type", "application/json");
		HttpEntity<byte[]> newAuctionEntity = new HttpEntity<byte[]>(body, headers);
		return newAuctionEntity;
	}	
	
	private byte[] marshalJson(Auction auction) throws IOException {
		return mapper.writeValueAsBytes(auction);
	}

	private <T> T unmarshallJson(ResponseEntity<String> response, Class<T> clazz)
			throws Exception {
		return mapper.readValue(new ByteArrayInputStream(response.getBody()
				.getBytes()), clazz);
	}

}
