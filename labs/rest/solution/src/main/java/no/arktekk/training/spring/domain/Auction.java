package no.arktekk.training.spring.domain;

import static no.arktekk.training.spring.util.DatabaseUtils.no_NO;
import static no.arktekk.training.spring.util.DatabaseUtils.timeStampFormatter;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
@XmlRootElement
public class Auction {

	@XmlAttribute
	private String id;

	@XmlElement
	private Double minimumPrice;

	@XmlElement
	private String description;

	@XmlElement
	private List<Album> albums;

	private DateTime starts;
	private DateTime expires;

	public Auction() {

	}

	public Auction(String id, Double minimumPrice, String description,
			DateTime starts, DateTime expires, List<Album> albums) {
		this.id = id;
		this.minimumPrice = minimumPrice;
		this.description = description;
		this.starts = starts;
		this.expires = expires;
		this.albums = albums;
	}

	public String getId() {
		return id;
	}

	public Double getMinimumPrice() {
		return minimumPrice;
	}

	public String getDescription() {
		return description;
	}

	public DateTime getStarts() {
		return starts;
	}

	public DateTime getExpires() {
		return expires;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	@XmlElement
	public String getStartDateTime() {
		return timeStampFormatter.print(getStarts().toDate(), no_NO);
	}

	public void setStartDateTime(String startDateTime) throws ParseException {
		starts = new DateTime(timeStampFormatter.parse(startDateTime, no_NO));

	}

	@XmlElement
	public String getExpiresDateTime() {
		return timeStampFormatter.print(getExpires().toDate(), no_NO);
	}

	public void setExpiresDateTime(String expiresDateTime)
			throws ParseException {
		expires = new DateTime(timeStampFormatter.parse(expiresDateTime, no_NO));

	}

	public void assignNewId() {
		id = UUID.randomUUID().toString();
	}
}
