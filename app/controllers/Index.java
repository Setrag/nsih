package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Index extends Controller {
    public static Result page() {
        return ok(index.render("Your new application is ready."));
    }
}

