package no.bekk.database.dao;

import no.bekk.database.model.Job;

import org.springframework.stereotype.Repository;

@Repository
public class JpaJobDao extends JpaDao<Long, Job> implements JobDao {

}
