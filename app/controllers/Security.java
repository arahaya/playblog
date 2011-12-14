package controllers;

import play.Play;
import play.libs.Crypto;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        String hashed = Crypto.passwordHash(password, Crypto.HashType.SHA512);
        String admin_username = Play.configuration.getProperty("admin.username");
        String admin_password = Play.configuration.getProperty("admin.password");

        if (username.equals(admin_username) && hashed.equals(admin_password)) {
            return true;
        } else {
            return false;
        }
    }
}