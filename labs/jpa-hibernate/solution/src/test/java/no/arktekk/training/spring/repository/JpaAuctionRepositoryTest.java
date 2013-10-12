package no.arktekk.training.spring.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import no.arktekk.training.spring.domain.Auction;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testApplicationContext.xml")
public class JpaAuctionRepositoryTest implements ApplicationContextAware {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AuctionRepository auctionRepository;

	private ApplicationContext ctx;

	/**
	 * Define a transaction manager
	 */
	@Test
	public void step_1() {
		AbstractPlatformTransactionManager transactionManager = ctx.getBean(AbstractPlatformTransactionManager.class);
		assertTrue(transactionManager instanceof JpaTransactionManager);
	}
	
	/**
	 * Refactor the findById method to use JPA instead of JdbcTemplate
	 */
	@Test
	public void step_2() throws Exception {
		Auction auction = auctionRepository.findById(1);
		assertEquals("Mint prog rock albums", auction.description());
	}

	/**
	 * Refactor the listAllRunningAuctions method to use JPA instead of
	 * JdbcTemplate
	 */
	@Test
	public void step_3() throws Exception {
		List<Auction> auctions = auctionRepository.listAllRunningAuctions();
		assertEquals(2, auctions.size());
	}

	/**
	 * Implement the method AuctionRepository.newAuction using JPA
	 * 
	 * Hint: Try adding an explicit flush after persist and see what happens
	 */
	@Test
	public void step_4() {
		int runningAuctions = auctionRepository.listAllRunningAuctions().size();
		
		Auction newAuction = new Auction(10.0, "Vacuum cleaner",
				DateTime.now(), DateTime.now().plusMonths(1));
		Auction created = auctionRepository.newAuction(newAuction);
		
		assertEquals(runningAuctions + 1,  auctionRepository.listAllRunningAuctions().size());
		assertTrue(created.id() > 0);
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
				this.ctx = ctx;
	}
	
}
