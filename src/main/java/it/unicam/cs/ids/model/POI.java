package it.unicam.cs.ids.model;

public class POI implements Content {

    private String id;

    private String name;

    private String description;

    private String author;

    private boolean approved;

    private double latitude;

    private double longitude;

    public POI(String id, String name, String description, String author, boolean approved, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.approved = approved;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isApproved() {
        return approved;
    }

    @Override
    public void setApproved(boolean state) {
        approved = state;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
