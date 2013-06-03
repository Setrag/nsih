package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import models.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render(Project.find.all(),Task.find.all()));
    }
    
    public static Result login() {
        return ok(login.render("Login window"));
    }
    
    public static Result scores() {
    	return ok(scores.render("Scores"));
    }
}

