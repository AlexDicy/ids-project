package it.unicam.cs.ids.model;

public class POI extends Content {

    private Coordinate coordinate;

    public POI(String id, String name, String description, String author, boolean approved, Coordinate coordinate) {
        super(id, name, description, author, approved);
        this.coordinate = coordinate;
    }

    public double getLatitude() {
        return coordinate.latitude();
    }

    public double getLongitude() {
        return coordinate.longitude();
    }

}
