package no.bekk.spring.demo.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import no.bekk.spring.demo.BrukerDAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/test-context.xml"})
public class BrukerDAOTest {

	@Autowired
	BrukerDAO dao;

	@Test
	public void testLesBruker() {
		assertThat(dao.lesBruker(1).navn, is("testbruker"));
	}

}
