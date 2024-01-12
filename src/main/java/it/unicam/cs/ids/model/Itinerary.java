package it.unicam.cs.ids.model;

import java.util.List;

public class Itinerary implements Content {
    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean isApproved() {
        return false;
    }

    @Override
    public void setApproved(boolean state) {

    }

    @Override
    public String getAuthor() {
        return null;
    }

    List<POI> getPoiList() {
        return null;
    }

    void addPoi(POI poi) {
    }

    void removePoi(POI poi) {
    }
}
