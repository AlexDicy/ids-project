package it.unicam.cs.ids.model.content;

import java.util.Date;

public class Image extends Content {
    private final String idPOI;

    public Image(String id, String name, String description, String author, Date creationDate, boolean approved, String idPOI) {
        super(name, description, author, approved, creationDate);
        this.idPOI = idPOI;
    }

    public String getIdPoi() {
        return idPOI;
    }
}
