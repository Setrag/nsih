package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Score extends Model {

    @Id
    public int id;
    public User auteur;
    
    public Score(int id, User auteur) {
      this.id = id;
      this.auteur = auteur;
    }

    public static Finder<String,Score> find = new Finder<String,Score>(
        String.class, Score.class
    );
}
