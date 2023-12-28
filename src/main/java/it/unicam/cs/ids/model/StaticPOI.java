package it.unicam.cs.ids.model;

public class StaticPOI implements  POI{

    private String id;

    private String name;

    private String description;

    private boolean approved;

    private double latitude;

    private double longitude;

    public StaticPOI(String id, String name, String description, boolean approved, double latitude, double longitude) {
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
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }
}
