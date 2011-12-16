package controllers;

import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.With;

// TODO: add a custom admin interface
@With(Secure.class)
public class Admin extends Controller {
    /**
     * Clear all cached objects
     */
    public static void clearCache() {
        Cache.clear();
        renderText("Cache cleared!");
    }
}