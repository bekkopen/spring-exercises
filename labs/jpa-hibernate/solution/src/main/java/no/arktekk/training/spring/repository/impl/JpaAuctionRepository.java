package no.arktekk.training.spring.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import no.arktekk.training.spring.domain.Auction;
import no.arktekk.training.spring.repository.AuctionRepository;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class JpaAuctionRepository implements AuctionRepository {

    @PersistenceContext 
    private EntityManager em;
    
    public List<Auction> listAllRunningAuctions() {
        DateTime now = new DateTime();
        return em.createQuery("from " + Auction.class.getName() + 
        		" where starts < :time and :time < expires", Auction.class)
        		.setParameter("time", now)
        		.getResultList();
    }

    public Auction findById(long auctionId) {
    	return em.find(Auction.class, auctionId);
    }

	@Override
	@Transactional
	public Auction newAuction(Auction newAuction) {
		em.persist(newAuction);
		return newAuction;
	}
}
