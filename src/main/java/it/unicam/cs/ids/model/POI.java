package it.unicam.cs.ids.model;

public interface POI extends Content {
    String getId();

    String getName();

    String getDescription();

    boolean isApproved();

    double getLatitude();

    double getLongitude();

    void setApproved(boolean state);
}
