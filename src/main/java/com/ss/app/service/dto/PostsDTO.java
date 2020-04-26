package com.ss.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.ss.app.domain.enumeration.CategoryPosts;

/**
 * A DTO for the {@link com.ss.app.domain.Posts} entity.
 */
public class PostsDTO implements Serializable {
    
    private String id;

    private String title;

    private String image;

    private String content;

    private CategoryPosts category;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CategoryPosts getCategory() {
        return category;
    }

    public void setCategory(CategoryPosts category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PostsDTO postsDTO = (PostsDTO) o;
        if (postsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostsDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", content='" + getContent() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
