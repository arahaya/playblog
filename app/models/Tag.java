package models;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Entity
@FilterDef(name = "published_posts")
public class Tag extends Model {
    public static Tag findBySlug(String slug) {
        return find("slug", slug).first();
    }
    
    @Column(nullable=false, unique=true)
    public String slug;

    @Column(nullable=false)
    public String name;

    @ManyToMany(mappedBy = "tags")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OrderBy("published DESC")
    //@Where(clause = "published < NOW()")
    public List<Post> posts;

    public Tag(String slug, String name) {
        this.slug = slug;
        this.name = name;
        this.posts = new ArrayList<Post>();
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", name, posts.size());
    }
}
