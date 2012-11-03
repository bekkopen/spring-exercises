package no.arktekk.training.spring.domain;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
@XmlRootElement
public class Album {
	
	@XmlAttribute
    private String id;
	
	@XmlElement
    private String title;
    
	@XmlElement
	private String artist;
	
    private Category category = new Category(1, "Progrock");
    private Label label = new Label(1,"Island records");

    public Album() {
    	
    }
    
    public Album(String id, String title, String artist, Category category, Label label) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.artist = artist;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Category getCategory() {
        return category;
    }


    public Label getLabel() {
        return label;
    }

    public void assignNewId() {
        id = UUID.randomUUID().toString();
    }
}
