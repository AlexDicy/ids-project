package it.unicam.cs.ids.model;

import java.util.Date;

public abstract class Content {
    private final String id;
    private final String name;
    private final String description;
    private boolean approved;
    private final String createdBy;
    private final Date creationDate;

    protected Content(String id, String name, String description, String createdBy, boolean approved, Date creationDate) {
        this.id = id;
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
