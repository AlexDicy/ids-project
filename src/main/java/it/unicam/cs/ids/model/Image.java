package it.unicam.cs.ids.model;

import java.util.Date;

public class Image extends Content {
    private final String idPOI;

    private final Date creationDate;

    public Image(String id, String name, String description, String author, Date creationDate, boolean approved, String idPOI) {
        super(id, name, description, author, approved);
        this.idPOI = idPOI;
        this.creationDate = creationDate;
    }

    public String getIdPoi() {
        return idPOI;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
