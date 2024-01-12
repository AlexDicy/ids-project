package it.unicam.cs.ids;

import it.unicam.cs.ids.model.ContentManager;
import it.unicam.cs.ids.model.POI;
import it.unicam.cs.ids.model.StaticPOI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class POIManager implements ContentManager<POI> {

    protected List<POI> poiList = new ArrayList<>();

    public POI get(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is not valid");
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

    @Override
    public void approve(String id) {
        POI poiToApprove = get(id);
        if (poiToApprove == null) {
            throw new IllegalArgumentException("POI not found");
        }
        poiToApprove.setApproved(true);
    }

    @Override
    public void remove(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is not valid");
        }

        poiList.removeIf(poi -> poi.getId().equals(id));
    }

    @Override
    public List<POI> getContentToApprove() {
        List<POI> toApprove = new ArrayList<>();
        for (POI poi : poiList) {
            if (!poi.isApproved()) {
                toApprove.add(poi);
            }
        }
        return toApprove;
    }
}
