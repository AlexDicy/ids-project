package it.unicam.cs.ids;

import it.unicam.cs.ids.model.POI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class POIManager implements ContentManager<POI> {

    protected List<POI> poiList = new ArrayList<>();

    @Override
    public POI get(String id) {
        Objects.requireNonNull(id, "Id is not valid");
        for (POI element : poiList) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public void submit(POI poi) {
        Objects.requireNonNull(poi, "Invalid poi");
        poiList.add(poi);
    }

    @Override
    public List<POI> getAll() {
        return new ArrayList<>(poiList);
    }

    @Override
    public List<POI> find(String query) {
        Objects.requireNonNull(query, "Query is not valid");
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
        Objects.requireNonNull(poiToApprove,"POI not found");
        poiToApprove.setApproved(true);
    }

    @Override
    public void remove(String id) {
        Objects.requireNonNull(id ,"Id is not valid");
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

}
