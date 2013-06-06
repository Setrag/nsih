package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;


@Entity
public class Score extends Model {

	@Id
	@GeneratedValue
	public int id;
    public float valeur;
    @ManyToOne
    public User auteur;
    @ManyToOne
    public Jeu jeu;
    
    public Score(float valeur, User auteur, Jeu jeu) {
      this.valeur = valeur;
      this.auteur = auteur;
      this.jeu = jeu;
    }

    public static Finder<String,Score> find = new Finder<String,Score>(
        String.class, Score.class
    );
}
