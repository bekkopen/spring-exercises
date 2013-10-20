package no.arktekk.training.spring.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.StringReader;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import no.arktekk.training.spring.domain.Auction;
import no.arktekk.training.spring.domain.AuctionList;
import no.arktekk.training.spring.service.AuctionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RestAuctionController {

	private final AuctionService auctionService;
	private final Jaxb2Marshaller jaxb2Marshaller;

	@Autowired
	public RestAuctionController(AuctionService auctionService,
			Jaxb2Marshaller jaxb2Marshaller) {
		this.auctionService = auctionService;
		this.jaxb2Marshaller = jaxb2Marshaller;
	}

	@RequestMapping(method = GET, value = "/auctions", produces = "application/xml")
	public ModelAndView listAuctions() {
		List<Auction> auctions = auctionService.allRunningAuctions();
		ModelAndView mav = new ModelAndView("jaxbMarshallerView");
		mav.addObject("auctions", new AuctionList(auctions));
		return mav;
	}

	@RequestMapping(method = GET, value = "/auctions/{auctionId}", produces = "application/xml")
	public ModelAndView getAuction(@PathVariable String auctionId) {
		Auction auction = auctionService.findById(auctionId);
		ModelAndView mav = new ModelAndView("jaxbMarshallerView");
		mav.addObject(auction);
		return mav;
	}

	@RequestMapping(method = POST, value = "/auctions", produces = "application/xml")
	public ModelAndView createAuction(@RequestBody String auctionXml) {

		Source source = new StreamSource(new StringReader(auctionXml));
		Auction auction = (Auction) jaxb2Marshaller.unmarshal(source);
		auctionService.newAuction(auction);

		ModelAndView mav = new ModelAndView("jaxbMarshallerView");
		mav.addObject(auction);
		return mav;
	}

	@RequestMapping(value = "/auctions", method = RequestMethod.GET)
	public @ResponseBody AuctionList listAuctionsJson() {
		return new AuctionList(auctionService.allRunningAuctions());
	}

	@RequestMapping(value = "/auctions/{auctionId}", method = RequestMethod.GET)
	public @ResponseBody Auction getAuctionJson(@PathVariable String auctionId) {
		Auction auction = auctionService.findById(auctionId);
		if (auction == null) {
			throw new RestException(HttpStatus.NOT_FOUND.value(),
					"Auction with id " + auctionId + " does not exist.");
		}
		return auction;
	}

	@RequestMapping(value = "/auctions", method = RequestMethod.POST)
	public ResponseEntity<Auction> createAuctionJson(@RequestBody Auction newAuction) {
		auctionService.newAuction(newAuction);
		return new ResponseEntity<Auction>(newAuction, HttpStatus.CREATED);
	}
	
	@ExceptionHandler(RestException.class)
	public @ResponseBody String handleException(RestException e, HttpServletResponse response) {
	    response.setStatus(e.getStatus());
	    return e.getMessage();
	}

}
