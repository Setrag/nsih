package controllers;

import play.*;
import play.mvc.*;
import play.api.templates.Html;
import play.data.*;

import views.html.*;

import models.*;

import play.mvc.BodyParser;                     
import play.libs.Json;
import play.libs.Json.*;                        
import scala.collection.mutable.StringBuilder;
import static play.libs.Json.toJson;
import org.codehaus.jackson.JsonNode;           
import org.codehaus.jackson.node.ObjectNode;

import com.avaje.ebean.Expression;

import java.util.*;

public class Application extends Controller {
	
    public static Result index() {
    	String username = session().get("email");
    	User user = null;
    	if (username != null) {
    		user = User.find.byId(username);
    	}
    	return ok(index.render(user, News.find.all()));
    }
    
    
    /*
     * GESTION DES SCORES
     */
    
    public static Result scores() {
    	return ok(scores.render("Scores", Score.find.orderBy().asc("jeu.nom").orderBy().desc("valeur").findList()));
    }
    
	public static Result scoresSymon() {
		List<Score> listScores = Score.find.where().eq("jeu.nom", "Symon").order().desc("valeur").findList();
		
		return ok(scoresSymon.render(listScores));
	}
	
	public static Result soumettreScore() {
    	float scoreVal = Float.parseFloat(request().queryString().get("score")[0]);
    	String userStr = request().queryString().get("user")[0];
    	String jeuStr = request().queryString().get("jeu")[0];
    	
    	User user = User.find.byId(userStr);
    	Jeu jeu = Jeu.find.byId(jeuStr);
    	if (user != null && jeu != null && scoreVal >= 0.0f) {
    		Score score = new Score(scoreVal, user, jeu);
    		score.save();
    		return ok("Score submitted successfully.");
    	} else {
    		return ok("Username not found.");
    	}
    }
	
	
	/*
	 * ESPACE PERSONNEL
	 */
	
	public static Result login() {
        return ok(login.render(Form.form(Login.class)) );
    }
    
    public static Result logout() {
		session().clear();
		flash("success", "Vous vous êtes déconnecté avec succès.");
		return redirect(
		    routes.Application.login()
		);
	}
    
    @Security.Authenticated(Secured.class)
    public static Result espacePerso() {
    	String username = session().get("email");
    	User user = null;
    	if (username != null) {
    		user = User.find.byId(username);
    	}
    	if (!user.isAdmin) {
    		return ok(espacePerso.render(user,
    			"Espace perso",
    			Score.find.where().eq("auteur.email", username).orderBy().asc("jeu.nom").orderBy().desc("valeur").findList()));
    	} else {
    		return ok(
    				espaceAdmin.render(user,
    						Score.find.where().eq("auteur.email", username).orderBy().asc("jeu.nom").orderBy().desc("valeur").findList(),
    						Form.form(FormSupprimerUtilisateur.class)
    				));
    	}
    	//return ok(espacePerso.render(user, "Espace perso", Score.find.where().select("*").fe ));
    }
    
    
    public static Result authenticate() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
		    return badRequest(login.render(loginForm));
		} else {
		    session().clear();
		    session("email", loginForm.get().email);
		    
		    User user = User.find.where().eq("email", loginForm.get().email).findUnique();
		    session("admin", Boolean.toString(user.isAdmin));
		    return redirect(
		        routes.Application.espacePerso()
		    );
		}
	}
    
    public static class Login {
    	public String email;
    	public String password;
    	
    	public String validate() {
			if (User.authenticate(email, password) == null) {
			  return "Nom d'utilisateur ou mot de passe invalide.";
			}
			return null;
		}
    }
    
    
    /* 
     * ADMINISTRATION
     */ 
    
    public static Result supprimerUtilisateur() {
		Form<FormSupprimerUtilisateur> deleteUserForm = Form.form(FormSupprimerUtilisateur.class).bindFromRequest();
		if (deleteUserForm.hasErrors()) {
		    return badRequest();
		} else {
		    User user = User.find.where().eq("email", deleteUserForm.get().email).findUnique();
		    if (user == null) {
		    	return badRequest("Utilisateur inconnu.");
		    } else if (user.email.equals(session().get("email"))) {
		    	return badRequest("Le suicide n'est pas la solution.");
		    }
		    
		    List<Score> listScore = Score.find.where().eq("auteur.email", user.email).findList();
		    for (Score score : listScore) {
		    	score.delete();
		    }
		     
		    List<News> listNews = News.find.where().eq("auteur.email", user.email).findList();
		    
		    for (News news : listNews) {
		    	news.delete();
		    }
		    
		    user.delete();
		    
		    return redirect(
		        routes.Application.espacePerso()
		    );
		}
	}
    
    public static class FormSupprimerUtilisateur {
    	public String email;
    	
    	public String validate() {
			if (User.find.where().idEq(email) == null) {
			  return "Nom d'utilisateur invalide.";
			}
			return null;
		}
    }
}
