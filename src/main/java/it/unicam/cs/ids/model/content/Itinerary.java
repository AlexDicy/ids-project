package it.unicam.cs.ids.model.content;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Document(collection = "itineraries")
public class Itinerary extends Content {
    @Field(targetType = FieldType.OBJECT_ID)
    private final List<String> poiList;

    public Itinerary(String name, String description, String createdBy, boolean approved, List<String> poiList) {
        super(name, description, createdBy, approved);
        this.poiList = poiList;
    }

    public List<String> getPoiList() {
        return poiList;
    }

    public void addPoi(String poi) {
        poiList.add(poi);
    }

    public void removePoi(String poiId) {
        poiList.remove(poiId);
    }
}
