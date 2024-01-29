package it.unicam.cs.ids.model.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.util.Date;

public abstract class Content {
    @Id
    private String id;
    private final String name;
    private final String description;
    private boolean approved;
    private final String createdBy;
    @CreatedDate
    private Date creationDate;
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getAuthorId() {
        return this.createdBy;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Float getSearchScore() {
        return searchScore;
    }
}
