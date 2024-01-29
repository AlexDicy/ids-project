package it.unicam.cs.ids.model.content;

import java.util.Date;

public class Image extends Content {
    private final String idPOI;

    private Image(String name, String description, String author, boolean approved, String idPOI) {
        super(name, description, author, approved);
        this.idPOI = idPOI;
    }

    public static Image temporaryCreateImage(String id, String name, String description, String author, Date creationDate, boolean approved, String idPOI) {
        return new Image(name, description, author, approved, idPOI);
    }

    public String getIdPoi() {
        return idPOI;
    }
}
