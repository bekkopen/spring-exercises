package no.arktekk.training.spring.repository;

import no.arktekk.training.spring.domain.Auction;

import java.util.List;


public interface AuctionRepository {
    List<Auction> listAllRunningAuctions();

    Auction findById(long auctionId);
    
    Auction newAuction(Auction newAuction);
}
