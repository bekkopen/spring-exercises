package no.bekk.database.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import no.bekk.database.log.LogAppender;
import no.bekk.database.model.Job;
import no.bekk.database.model.JobbEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.classic.Level;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testApplicationContext.xml")
public class JpaJobbDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private JobDao jobbDao;

	private LogAppender logAppender;

	@Before
	public void setUp() {

		makeJobs(10);

		em.flush();
		em.clear();
	}

	private void makeJobs(final int count) {

		for (int i = 0; i < count; i++) {
			Job jobb = new Job();
			em.persist(jobb);
			for (int j = 0; j < 3; j++) {
				JobbEvent jobbEvent = new JobbEvent("test" + j, jobb);
				em.persist(jobbEvent);
			}

		}
	}

	@Test
	public void skalKunGjoere2SpoerringerMedBatchSize10() {
		assertEquals(10, jobbDao.count());

		logAppender = new LogAppender(Level.INFO, "jdbc.sqlonly");

		List<Job> all = jobbDao.getAll();

		for (Job jobb : all) {
			System.out.println(jobb.getEvents());
		}
		assertEquals(2, logAppender.getLogEvents().size());
	}

}
