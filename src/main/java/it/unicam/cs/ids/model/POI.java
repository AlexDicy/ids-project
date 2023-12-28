package it.unicam.cs.ids.model;

public interface POI {
    String getId();
    String getName();
    String getDescription();
    boolean isApproved();
    double getLatitude();
    double getLongitude();
}
