package it.unicam.cs.ids.model.content;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "itineraries")
public class Itinerary extends Content {
    private final List<POI> poiList;

    public Itinerary(String name, String description, String createdBy, boolean approved, List<POI> poiList) {
        super(name, description, createdBy, approved);
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
