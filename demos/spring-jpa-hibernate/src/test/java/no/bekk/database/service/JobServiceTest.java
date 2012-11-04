package no.bekk.database.service;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import no.bekk.database.dao.JobDao;
import no.bekk.database.model.Job;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration("classpath:testApplicationContext.xml")
public class JobServiceTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private JobService jobService;

	@Autowired
	private JobDao jobDao;

	@PersistenceContext
	private EntityManager em;

	@Test
	// Hva er problemet her?
	public void test1() {
		Job job = new Job("name");
		em.persist(job);
		long id = job.getId();
		em.flush();
		em.clear();
		jobService.updateJobName(id, "newname");
		em.flush();
		em.clear();
		assertEquals("newname", em.find(Job.class, id).getName());
	}

	@Test
	// Hvorfor fungerer denne?
	public void test2() {
		Job job = new Job("name");
		jobDao.persist(job);
		long id = job.getId();
		jobService.updateJobName(id, "newname");
		final Job job2 = jobDao.findById(id);
		assertEquals("newname", job2.getName());
	}
}
