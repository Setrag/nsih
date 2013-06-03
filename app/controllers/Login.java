package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import models.*;

public class Login extends Controller {
  
    public static Result page() {
        return ok(login.render("Login window"));
    }
}

