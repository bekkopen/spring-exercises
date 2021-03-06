package no.bekk.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "JOBB_EVENT")
public class JobbEvent {

	private static final String JOBB_EVENT_SEQ = "JOBB_EVENT_SEQ";

	@SuppressWarnings("unused")
	@Id
	@GenericGenerator(name = JOBB_EVENT_SEQ, strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = JOBB_EVENT_SEQ), @Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "100"),
			@Parameter(name = "optimizer", value = "org.hibernate.id.enhanced.OptimizerFactory.HiLoOptimizer") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = JOBB_EVENT_SEQ)
	@Column(name = "ID")
	private long id;

	@SuppressWarnings("unused")
	@Column(name = "MELDING")
	private String melding;

	public JobbEvent(final String melding, final Job jobb) {
		super();
		this.melding = melding;
		this.jobb = jobb;
	}

	@SuppressWarnings("unused")
	@ManyToOne
	@JoinColumn(name = "JOBB_ID")
	private Job jobb;

	@SuppressWarnings("unused")
	private JobbEvent() {
	}

}
