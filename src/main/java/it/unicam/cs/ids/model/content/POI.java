package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.Coordinate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pois")
public class POI extends Content {

    private Coordinate coordinate;

    public POI(String name, String description, String createdBy, boolean approved, Coordinate coordinate) {
        super(name, description, createdBy, approved);
        this.coordinate = coordinate;
    }

    public double getLatitude() {
        return coordinate.latitude();
    }

    public double getLongitude() {
        return coordinate.longitude();
    }

}
