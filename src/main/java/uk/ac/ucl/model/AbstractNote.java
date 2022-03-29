package uk.ac.ucl.model;

import java.time.LocalDateTime;
import java.util.Map;

public abstract class AbstractNote<T> implements Note<T> {
    private int id;
    private String title;
    private String label;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    T content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public abstract Map<String, T> getContent();

    public void setContent(T content) {
        this.content = content;
    }
    public String toString() {
        return "Note " + id + ": " + title + content;
    }
}
