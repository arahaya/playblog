package models;

import controllers.CRUD;
import org.hibernate.annotations.Index;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.mvc.Router;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Post extends Model {
    public static Post findBySlugAndDate(String slug, Date date) {
        String hql = "" +
                "SELECT p" +
                "  FROM Post p" +
                " WHERE published < NOW()" +
                "   AND slug = :slug" +
                "   AND year(published) = year(:date)" +
                "   AND month(published) = month(:date)";
        return find(hql).bind("slug", slug).bind("date", date).first();
    }

    public static List<Post> findActive(int page, int length) {
        String hql = "" +
                "SELECT p" +
                "  FROM Post p" +
                " WHERE published < NOW()" +
                " ORDER BY published DESC";
        return find(hql).fetch(page, length);
    }

    public static List<Post> findByYear(Date date, int page, int length) {
        String hql = "" +
                "SELECT p" +
                "  FROM Post p" +
                " WHERE published < NOW()" +
                "   AND year(published) = year(:date)" +
                " ORDER BY published DESC";
        return find(hql).bind("date", date).fetch(page, length);
    }

    public static List<Post> findByMonth(Date date, int page, int length) {
        String hql = "" +
                "SELECT p" +
                "  FROM Post p" +
                " WHERE published < NOW()" +
                "   AND year(published) = year(:date)" +
                "   AND month(published) = month(:date)" +
                " ORDER BY published DESC";
        return find(hql).bind("date", date).fetch(page, length);
    }

    public static List<Post> findByCategory(Category category, int page, int length) {
        String hql = "" +
                "SELECT p" +
                "  FROM Post p" +
                " WHERE published < NOW()" +
                "   AND category = :category" +
                " ORDER BY published DESC";
        return find(hql).bind("category", category).fetch(page, length);
    }

    public static List<Post> findByTag(Tag tag, int page, int length) {
        String hql = "" +
                "SELECT DISTINCT p" +
                "  FROM Post p" +
                "  JOIN p.tags t" +
                " WHERE published < NOW()" +
                "   AND t = :tag" +
                " ORDER BY published DESC";
        return find(hql).bind("tag", tag).fetch(page, length);
    }

    public static long countActive() {
        String hql = "" +
                "SELECT COUNT(p)" +
                "  FROM Post p" +
                " WHERE published < NOW()";
        return find(hql).first();
    }

    public static long countByYear(Date date) {
        String hql = "" +
                "SELECT COUNT(p)" +
                "  FROM Post p" +
                " WHERE published < NOW()" +
                "   AND year(published) = year(:date)";
        return find(hql).bind("date", date).first();
    }

    public static long countByMonth(Date date) {
        String hql = "" +
                "SELECT COUNT(p)" +
                "  FROM Post p" +
                " WHERE published < NOW()" +
                "   AND year(published) = year(:date)" +
                "   AND month(published) = month(:date)";
        return find(hql).bind("date", date).first();
    }

    public static long countByCategory(Category category) {
        String hql = "" +
                "SELECT COUNT(p)" +
                "  FROM Post p" +
                " WHERE published < NOW()" +
                "   AND category = :category";
        return find(hql).bind("category", category).first();
    }
    
    public static long countByTag(Tag tag) {
        String hql = "" +
                "SELECT COUNT(DISTINCT p)" +
                "  FROM Post p" +
                "  JOIN p.tags t" +
                " WHERE published < NOW()" +
                "   AND t = :tag";
        return find(hql).bind("tag", tag).first();
    }
    

    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    @Required
    public Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    public Set<Tag> tags;

    @Column(nullable = false, unique = true)
    @Required
    public String slug;

    @Column(nullable = false)
    @Required
    public String title;

    @Column(nullable = false)
    @Lob
    @Required
    @MaxSize(100000)
    public String content;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Index(name = "post_published_idx")
    public Date published;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CRUD.Exclude
    public Date created;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CRUD.Exclude
    public Date updated;

    @PrePersist
    protected void onCreate() {
        updated = created = new Date();

        if (published == null) {
            published = created;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
    
    public Post(String slug, String title, String content, Date published) {
        this.slug = slug;
        this.title = title;
        this.content = content;
        this.published = published;
    }

    public Post previous() {
        return Post.find("published < ? ORDER BY published DESC", published).first();
    }

    public Post next() {
        return Post.find("published > ? AND published < NOW() ORDER BY published ASC", published).first();
    }

    public List<Post> related() {
        return related(3);
    }

    public List<Post> related(int limit) {
        String hql = "" +
                "SELECT DISTINCT p" +
                "  FROM Post p" +
                "  JOIN p.tags t" +
                " WHERE p != :post" +
                "   AND published < NOW()" +
                "   AND t IN (:tags)" +
                " GROUP BY p" +
                " ORDER BY COUNT(p) DESC, published DESC";
        return Post.find(hql).bind("post", this).bind("tags", this.tags).fetch(limit);
    }

    public String permalink() {
        return permalink(false);
    }

    public String permalink(boolean absolute) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("date", new SimpleDateFormat("yyyy/MM").format(published));
        args.put("slug", slug);

        return absolute ? Router.getFullUrl("Blog.post", args) : Router.reverse("Blog.post", args).url;
    }

    @Override
    public String toString() {
        return String.format("%s %s", new SimpleDateFormat("yyyy/MM/dd").format(published), title);
    }
}
