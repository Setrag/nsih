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

public class Application extends Controller {
  
  	
    public static Result index() {
        return ok(index.render(Project.find.all(),Task.find.all()));
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
    	return ok(scores.render("Scores"));
    }
    
    @BodyParser.Of(BodyParser.Json.class)
	public static Result scoresJson() {
		JsonNode json = request().body().asJson();
		ObjectNode result = Json.newObject();
		String name = ""; //= json.findPath("name").getTextValue();
		if(name == null) {
			result.put("status", "KO");
			result.put("message", "Missing parameter [name]");
			return badRequest(result);
		} else {
			result.put("status", "OK");
			result.put("message", "Hello " + name);
			return ok(result);
		}
	}
    
    @Security.Authenticated(Secured.class)
    public static Result espacePerso() {
    	return ok(espacePerso.render("Espace perso"));
    }
    
    public static Result authenticate() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
		    return badRequest(login.render(loginForm));
		} else {
		    session().clear();
		    session("email", loginForm.get().email);
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

