package it.unicam.cs.ids.model.content;

public class Image extends Content {
    private final String idPOI;

    public Image(String name, String description, String author, boolean approved, String idPOI) {
        super(name, description, author, approved);
        this.idPOI = idPOI;
    }

    public String getIdPoi() {
        return idPOI;
    }
}
