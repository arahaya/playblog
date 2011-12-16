package controllers;

import models.Category;
import models.Post;
import models.Tag;
import play.cache.CacheFor;
import play.data.binding.As;
import play.mvc.Controller;

import java.util.Date;
import java.util.List;

public class Blog extends Controller {
    public static void index() {

    }
    
    public static void archives(int page) {
        long total = Post.countActive();
        List<Post> posts = Post.findActive(page, 10);
        render(posts, page, total);
    }

    public static void yearly(@As("yyyy") Date date, int page) {
        long total = Post.countByYear(date);
        List<Post> posts = Post.findByYear(date, page, 10);
        render(posts, date, page, total);
    }

    public static void monthly(@As("yyyy/MM") Date date, int page) {
        notFoundIfNull(date); // invalid date like 2011/13 was passed

        long total = Post.countByMonth(date);
        List<Post> posts = Post.findByMonth(date, page, 10);
        render(posts, date, page, total);
    }

    public static void category(String slug, int page) {
        Category category = Category.findBySlug(slug);
        notFoundIfNull(category);

        long total = Post.countByCategory(category);
        List<Post> posts = Post.findByCategory(category, page, 10);
        render(posts, category, page, total);
    }
    
    public static void tag(String slug, int page) {
        Tag tag = Tag.findBySlug(slug);
        notFoundIfNull(tag);

        long total = Post.countByTag(tag);
        List<Post> posts = Post.findByTag(tag, page, 10);
        render(posts, tag, page, total);
    }

    @CacheFor("1h")
    public static void post(@As("yyyy/MM") Date date, String slug) {
        Post post = Post.findBySlugAndDate(slug, date);
        notFoundIfNull(post);

        render(post);
    }

    public static void atom() {
        List<Post> posts = Post.findActive(1, 10);
        render(posts);
    }
}