package it.unicam.cs.ids.model;

public interface Content {
    String getId();

    String getName();

    String getDescription();

    boolean isApproved();

    void setApproved(boolean state);

    String getAuthor();

}
