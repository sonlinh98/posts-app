package com.ss.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

import com.ss.app.domain.enumeration.CategoryPosts;

/**
 * A Posts.
 */
@Document(collection = "posts")
public class Posts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("image")
    private String image;

    @Field("content")
    private String content;

    @Field("category")
    private CategoryPosts category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Posts title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public Posts image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public Posts content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CategoryPosts getCategory() {
        return category;
    }

    public Posts category(CategoryPosts category) {
        this.category = category;
        return this;
    }

    public void setCategory(CategoryPosts category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Posts)) {
            return false;
        }
        return id != null && id.equals(((Posts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Posts{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", content='" + getContent() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
