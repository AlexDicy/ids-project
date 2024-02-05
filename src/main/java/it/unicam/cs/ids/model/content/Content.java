package it.unicam.cs.ids.model.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public abstract class Content {
    @Getter
    @Id
    private String id;
    @Getter
    private final String name;
    @Getter
    private final String description;
    @Setter
    @Getter
    private boolean approved;
    private final String createdBy;
    @Getter
    @CreatedDate
    private LocalDateTime creationDate;
    /**
     * The score of the content, used for full text search ranking.
     * Should be null otherwise.
     */
    private Float searchScore;

    protected Content(String name, String description, String createdBy, boolean approved) {
        this.name = name;
        this.description = description;
        this.approved = approved;
        this.createdBy = createdBy;
    }

    public String getAuthorId() {
        return this.createdBy;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Float getSearchScore() {
        return searchScore;
    }
}
