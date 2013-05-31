package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class News extends Model {

	@Id
	public int id;
	public String titre;
	public String contenu;

	public News(int id, String titre, String contenu) {
		this.id = id;
		this.titre = titre;
		this.contenu = contenu;
	}
	
	public static Finder<String,News> find = new Finder<String,News>(
        String.class, News.class
    );
}
