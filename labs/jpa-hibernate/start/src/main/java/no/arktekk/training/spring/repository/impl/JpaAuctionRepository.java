package no.arktekk.training.spring.repository.impl;

import java.util.List;

import no.arktekk.training.spring.domain.Auction;
import no.arktekk.training.spring.repository.AuctionRepository;

import org.springframework.stereotype.Repository;


@Repository
public class JpaAuctionRepository implements AuctionRepository {

    public List<Auction> listAllRunningAuctions() {
    	return null;
    }

    public Auction findById(long auctionId) {
    	return null;
    }

	@Override
	public Auction newAuction(Auction newAuction) {
		return null;
	}
}
