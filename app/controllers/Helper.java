package controllers;

import play.mvc.Controller;

public class Helper extends Controller {
    /**
     * Add a trailing slash to the current URL
     */
    public static void addSlash() {
        redirect(request.url + "/", true);
    }
}
