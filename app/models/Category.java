package models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends Model {
    public static Category findBySlug(String slug) {
        return find("slug", slug).first();
    }

    @Column(nullable=false, unique=true)
    public String slug;

    @Column(nullable=false)
    public String name;

    @OneToMany(mappedBy = "category")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OrderBy("published DESC")
    public List<Post> posts;

    public Category(String slug, String name) {
        this.slug = slug;
        this.name = name;
        this.posts = new ArrayList<Post>();
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", name, posts.size());
    }
}
