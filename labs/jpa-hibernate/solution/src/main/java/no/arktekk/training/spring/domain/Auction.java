package no.arktekk.training.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity(name = "AUCTIONS")
public class Auction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	@Column(name = "minimumPrice")
    private double minimumPrice;
	@Column(name = "description")
    private String description;
	@Column(name = "starts")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime starts;
	@Column(name = "expires")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime expires;

	Auction() {
	}

	public Auction(double minimumPrice, String description, DateTime starts, DateTime expires) {
		this.minimumPrice = minimumPrice;
		this.description = description;
		this.starts = starts;
		this.expires = expires;
	}
	
	public Auction(long id, double minimumPrice, String description, DateTime starts, DateTime expires) {
		this(minimumPrice, description, starts, expires);
        this.id = id;
    }

    public long id() {
        return id;
    }

    public double minimumPrice() {
        return minimumPrice;
    }

    public String description() {
        return description;
    }

    public DateTime starts() {
        return starts;
    }

    public DateTime expires() {
        return expires;
    }

}
