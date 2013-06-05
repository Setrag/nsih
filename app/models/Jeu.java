package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Jeu extends Model {

    @Id
    public String nom;

    public Jeu(String nom) {
    	this.nom = nom;
    }

    public static Finder<String,Jeu> find = new Finder<String,Jeu>(
        String.class, Jeu.class
    );
}
