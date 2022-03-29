package uk.ac.ucl.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

public interface Note<T> extends Serializable {

    int getId();

    void setId(int id);

    String getTitle();

    void setTitle(String title);

    String getLabel();

    void setLabel(String label);

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getUpdatedAt();

    void setUpdatedAt(LocalDateTime updatedAt);

    Map<String, T> getContent();

    void setContent(T content);
}
