package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Scores extends Controller {
	public static Result page() {
    	return ok(index.render("Scores"));
    }
}

