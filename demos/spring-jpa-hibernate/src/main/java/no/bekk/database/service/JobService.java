package no.bekk.database.service;

import java.util.List;

import no.bekk.database.model.Job;

public interface JobService {

	List<Job> getJobs();

	void updateJobName(long id, String name);

	void updateJobNameProgrammaticTransaction(long id, String name);

}
