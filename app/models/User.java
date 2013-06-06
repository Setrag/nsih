package models;

import java.util.List;

import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class User extends Model {

    @Id
    public String email;
    public String name;
    public String password;
    public boolean isAdmin;
    
    
    public User(String email, String name, String password) {
      this.email = email;
      this.name = name;
      this.password = password;
      this.isAdmin = false;
    }
    
    public User(String email, String name, String password, boolean isAdmin) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    
    
    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }
    
    public static Model.Finder<String,User> find = new Model.Finder<String,User>(
            String.class, User.class
    );
}
