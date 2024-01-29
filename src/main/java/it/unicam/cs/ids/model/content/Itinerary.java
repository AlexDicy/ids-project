package it.unicam.cs.ids.model.content;

import java.util.Date;
import java.util.List;

public class Itinerary extends Content {
    private final List<POI> poiList;

    public Itinerary(String id, String name, String description, String createdBy, boolean approved, Date cretionDate, List<POI> poiList) {
        super(name, description, createdBy, approved, cretionDate);
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
