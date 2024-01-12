package it.unicam.cs.ids.model;

import java.util.List;

public class Itinerary implements Content {

    private String id;
    private String name;
    private String description;
    private String author;
    private boolean approved;

    private List<POI> poiList;

    public Itinerary(String id, String name, String description, String author, boolean approved, List<POI> poiList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.approved = approved;
        this.poiList = poiList;
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

    List<POI> getPoiList() {
        return poiList;
    }

    void addPoi(POI poi) {
        poiList.add(poi);
    }

    void removePoi(String poiId) {
        poiList.removeIf(poi -> poi.getId().equals(poiId));
    }
}
