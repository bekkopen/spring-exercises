package no.arktekk.training.spring.repository.impl;

import no.arktekk.training.spring.domain.Auction;
import no.arktekk.training.spring.mapper.AuctionMapper;
import no.arktekk.training.spring.repository.AuctionRepository;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import java.util.List;

import static no.arktekk.training.spring.util.DatabaseUtils.no_NO;
import static no.arktekk.training.spring.util.DatabaseUtils.timeStampFormatter;


@Repository
public class JpaAuctionRepository implements AuctionRepository {
    private final JdbcTemplate template;

    @PersistenceContext 
    private EntityManager em;
    
    @Autowired
    public JpaAuctionRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public List<Auction> listAllRunningAuctions() {
        DateTime now = new DateTime();
        return em.createQuery("from " + Auction.class.getName() + 
        		" where starts < :time and :time < expires", Auction.class)
        		.setParameter("time", now)
        		.getResultList();
//        return template.query(
//                "select * from Auctions where ? between starts and expires",
//                new AuctionMapper(),
//                timeStampFormatter.print(now.toDate(), no_NO));
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
