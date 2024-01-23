package it.unicam.cs.ids.model;

public abstract class Content {
    private final String id;
    private final String name;
    private final String description;
    private boolean approved;
    private final String author;

    protected Content(String id, String name, String description, String author, boolean approved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.approved = approved;
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

    public String getAuthor() {
        return author;
    }
}
