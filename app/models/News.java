package models;

import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class News extends Model {

	public enum TypeNews {
		INFO,
		EVENT,
		SCORES
	}
	
	@Id
	public int id;
	public String titre;
	public String contenu;
	public TypeNews typeN;
	
	@ManyToOne
	public User auteur;
	
	public News(int id, String titre, String contenu, TypeNews type) {
		this.id = id;
		this.titre = titre;
		this.contenu = contenu;
		this.typeN = type;
	}
	
	public static Model.Finder<String,News> find = new Model.Finder<String,News>(
        String.class, News.class
    );
}
