package controllers;

import play.mvc.Controller;

public class Helper extends Controller {
    public static void addSlash() {
        redirect(request.url + "/");
    }
}
