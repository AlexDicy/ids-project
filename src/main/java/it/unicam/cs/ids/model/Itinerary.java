package it.unicam.cs.ids.model;

import java.util.List;

public class Itinerary extends Content {
    private final List<POI> poiList;

    public Itinerary(String id, String name, String description, String author, boolean approved, List<POI> poiList) {
        super(id, name, description, author, approved);
        this.poiList = poiList;
    }

    public List<POI> getPoiList() {
        return poiList;
    }

    public void addPoi(POI poi) {
        poiList.add(poi);
    }

    public void removePoi(String poiId) {
        poiList.removeIf(poi -> poi.getId().equals(poiId));
    }
}
