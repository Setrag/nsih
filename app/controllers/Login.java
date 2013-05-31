package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Login extends Controller {
    public static Result page() {
        return ok(index.render("Login."));
    }
}

