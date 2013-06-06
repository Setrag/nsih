package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import views.html.*;

import models.*;

import play.mvc.BodyParser;                     
import play.libs.Json;
import play.libs.Json.*;                        
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
    	return ok(index.render(user, Project.find.all(),Task.find.all()));
    }
    
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
    
    public static Result scores() {
    	return ok(scores.render("Scores", Score.find.orderBy().asc("jeu.nom").orderBy().desc("valeur").findList()));
    }
    
    @BodyParser.Of(BodyParser.Json.class)
	public static Result scoresJson() {
		JsonNode json = request().body().asJson();
		ObjectNode result = Json.newObject();
		String name = ""; //= json.findPath("name").getTextValue();
		List<Score> listScores = Score.find.all();
		
		for(Iterator<Score> i = listScores.iterator(); i.hasNext(); ) {
		  Score item = i.next();
		  result.put("score", Float.toString(item.valeur));
		  result.put("jeu", item.jeu.nom);
		  result.put("auteur", item.auteur.email);
		}
		
		return ok(result);

	}
    
    @Security.Authenticated(Secured.class)
    public static Result espacePerso() {
    	String username = session().get("email");
    	User user = null;
    	if (username != null) {
    		user = User.find.byId(username);
    	}
    	return ok(espacePerso.render(user, "Espace perso", Score.find.where().eq("auteur.email", username).orderBy().asc("jeu.nom").orderBy().desc("valeur").findList()));
    	//return ok(espacePerso.render(user, "Espace perso", Score.find.where().select("*").fe ));
    }
    
    public static Result soumettreScore() {
    	float scoreVal = Float.parseFloat(request().queryString().get("score")[0]);
    	String userStr = request().queryString().get("user")[0];
    	String jeuStr = request().queryString().get("jeu"));
    	return ok();
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
}

