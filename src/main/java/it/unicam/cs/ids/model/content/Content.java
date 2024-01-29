package it.unicam.cs.ids.model.content;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.util.Date;

public abstract class Content {
    @Id
    private String id;
    @TextIndexed(weight = 2)
    private final String name;
    @TextIndexed
    private final String description;
    private boolean approved;
    private final String createdBy;
    private final Date creationDate;

    protected Content(String name, String description, String createdBy, boolean approved, Date creationDate) {
        this.name = name;
        this.description = description;
        this.approved = approved;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
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
}
