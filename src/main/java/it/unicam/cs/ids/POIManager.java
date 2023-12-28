package it.unicam.cs.ids;

import it.unicam.cs.ids.model.POI;

import java.util.ArrayList;
import java.util.List;

public class POIManager {

    protected List<POI> poiList = new ArrayList<>();

    public POI get(String id) {
        if (id == null) {
            return null;
        }

        for (POI element : poiList) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        return null;
    }

    public void submit(POI poi) {
        if (poi == null) {
            throw new IllegalArgumentException("Invalid poi");
        }

        poiList.add(poi);
    }

    public List<POI> getAll() {
        return new ArrayList<>(poiList);
    }

    public List<POI> getInRange(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
        if (fromLatitude > toLatitude || fromLongitude > toLongitude) {
            throw new IllegalArgumentException("Invalid latitude or longitude range");
        }

        List<POI> poisInRange = new ArrayList<>();

        for (POI element : poiList) {
            double latitude = element.getLatitude();
            double longitude = element.getLongitude();

            if (latitude >= fromLatitude && latitude <= toLatitude &&
                    longitude >= fromLongitude && longitude <= toLongitude) {
                poisInRange.add(element);
            }
        }

        return poisInRange;
    }

    public List<POI> find(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Invalid query");
        }

        List<POI> pois = new ArrayList<>();

        for (POI element : poiList) {
            if (element.getName().contains(query) || element.getDescription().contains(query)) {
                pois.add(element);
            }
        }

        return pois;
    }
}
