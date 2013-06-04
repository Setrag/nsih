package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Score extends Model {

	@Id
	public int id;
    public float valeur;
    @ManyToOne
    public User auteur;
    @ManyToOne
    public Jeu jeu;
    
    public Score(int id, float valeur, User auteur, Jeu jeu) {
      this.id = id;
      this.valeur = valeur;
      this.auteur = auteur;
      this.jeu = jeu;
    }

    public static Finder<String,Score> find = new Finder<String,Score>(
        String.class, Score.class
    );
}
