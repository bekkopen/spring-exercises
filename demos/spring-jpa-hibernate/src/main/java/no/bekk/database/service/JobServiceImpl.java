package no.bekk.database.service;

import java.util.List;

import no.bekk.database.dao.JobDao;
import no.bekk.database.model.Job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
// Hva er et mulig problem her med transaksjonene?
public class JobServiceImpl implements JobService {

	private final JobDao jobDao;

	private final TransactionTemplate transactionTemplate;

	@Autowired
	public JobServiceImpl(final JobDao jobDao, final TransactionTemplate transactionTemplate) {
		this.jobDao = jobDao;
		this.transactionTemplate = transactionTemplate;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Job> getJobs() {
		return jobDao.getAll();
	}

	@Override
	@Transactional
	public void updateJobName(final long id, final String name) {
		final Job job = jobDao.getById(id);
		job.setName(name);
	}

	@Override
	public void updateJobNameProgrammaticTransaction(final long id, final String name) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(final TransactionStatus status) {
				final Job job = jobDao.getById(id);
				job.setName(name);
			}
		});
	}

}
