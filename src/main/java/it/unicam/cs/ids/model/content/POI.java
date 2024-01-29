package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.Coordinate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "pois")
public class POI extends Content {

    private Coordinate coordinate;

    public POI(String name, String description, String createdBy, boolean approved, Coordinate coordinate) {
        super(name, description, createdBy, approved);
        this.coordinate = coordinate;
    }

    public static POI temporaryCreatePOI(String id, String name, String description, String author, boolean approved, Date creationDate, Coordinate coordinate) {
        return new POI(name, description, author, approved, coordinate);
    }

    public double getLatitude() {
        return coordinate.latitude();
    }

    public double getLongitude() {
        return coordinate.longitude();
    }

}
