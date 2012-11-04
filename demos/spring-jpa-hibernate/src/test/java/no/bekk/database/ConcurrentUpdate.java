package no.bekk.database;

import javax.annotation.Resource;

import no.bekk.database.dao.JobDao;
import no.bekk.database.model.Job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class ConcurrentUpdate extends AbstractJUnit4SpringContextTests {

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private JobDao jobbDao;

	@Test
	public void test() {

		final Job jobb = new Job();
		jobb.setName("name");
		jobb.setFlag(true);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(final TransactionStatus status) {
				jobbDao.persist(jobb);
			}
		});

		final Thread thread1 = new Thread(new UpdateInTransaction(jobb.getId(), "name1", true));
		final Thread thread2 = new Thread(new UpdateInTransaction(jobb.getId(), "name1", true));

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private class UpdateInTransaction implements Runnable {

		private final long id;
		private final String name;
		private final boolean flag;

		public UpdateInTransaction(final long id, final String name, final boolean flag) {
			this.id = id;
			this.name = name;
			this.flag = flag;
		}

		@Override
		public void run() {
			try {
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(final TransactionStatus status) {
						final Job jobb = jobbDao.findById(id);
						jobb.setName(name);
						jobb.setFlag(flag);
					}
				});
			} catch (OptimisticLockingFailureException e) {
				System.out.println(e);
			}
		}
	}

}
