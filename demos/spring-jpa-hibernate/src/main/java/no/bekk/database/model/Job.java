package no.bekk.database.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "JOB")
public class Job {

	static final Logger LOG = LoggerFactory.getLogger(Job.class);
	private static final String JOBB_SEQ = "JOBB_SEQ";

	@Id
	@GenericGenerator(name = JOBB_SEQ, strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = JOBB_SEQ), @Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "100"),
			@Parameter(name = "optimizer", value = "org.hibernate.id.enhanced.OptimizerFactory.HiLoOptimizer") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = JOBB_SEQ)
	@Column(name = "ID")
	private long id;

	@Version
	@Column(name = "VERSION")
	protected int version;

	@Column(name = "NAME")
	private String name;

	@Column(name = "FLAG")
	private boolean flag;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(final boolean flag) {
		this.flag = flag;
	}

	@OneToMany(mappedBy = "jobb", fetch = FetchType.LAZY)
	private Set<JobbEvent> events;

	public Job() {
		super();
	}

	public Job(final String name) {
		this.name = name;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public Set<JobbEvent> getEvents() {
		return events;
	}

}
