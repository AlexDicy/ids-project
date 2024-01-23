package it.unicam.cs.ids;

import it.unicam.cs.ids.model.Coordinate;
import it.unicam.cs.ids.model.Municipality;
import it.unicam.cs.ids.model.POI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class POIManager implements ContentManager<POI> {

    private final Municipality municipality;
    protected List<POI> poiList = new ArrayList<>();

    public POIManager(Municipality municipality) {
        this.municipality = municipality;
    }

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
        Objects.requireNonNull(poiToApprove, "POI not found");
        poiToApprove.setApproved(true);
    }

    @Override
    public void remove(String id) {
        Objects.requireNonNull(id, "Id is not valid");
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

    public List<POI> getInRange(Coordinate fromCoordinate, Coordinate toCoordinate) {
        if (fromCoordinate.latitude() > toCoordinate.latitude() || fromCoordinate.longitude() > toCoordinate.longitude()) {
            throw new IllegalArgumentException("Invalid latitude or longitude range");
        }

        List<POI> poisInRange = new ArrayList<>();

        for (POI element : poiList) {
            double latitude = element.getLatitude();
            double longitude = element.getLongitude();

            if (latitude >= fromCoordinate.latitude() && latitude <= toCoordinate.latitude() && longitude >= fromCoordinate.longitude() && longitude <= toCoordinate.longitude()) {
                poisInRange.add(element);
            }
        }

        return poisInRange;
    }

    /**
     * Check if the coordinate is inside the perimeter area of the Municipality
     *
     * @param coordinate the coordinate to check
     * @return true if the coordinate is inside the perimeter area of the Municipality, false otherwise
     */
    public boolean checkCoordinate(Coordinate coordinate) {
        List<Coordinate> coordinates = municipality.perimeter();
        // we're going to use a ray-casting/jordan curve theorem algorithm to check if the coordinate is inside the perimeter area of the Municipality
        int intersections = 0;
        for (int i = 0, j = coordinates.size() - 1; i < coordinates.size(); j = i++) {
            Coordinate c1 = coordinates.get(i);
            Coordinate c2 = coordinates.get(j);
            if ((c1.latitude() > coordinate.latitude()) != (c2.latitude() > coordinate.latitude())) {
                if (coordinate.longitude() < (c2.longitude() - c1.longitude()) * (coordinate.latitude() - c1.latitude()) / (c2.latitude() - c1.latitude()) + c1.longitude()) {
                    intersections++;
                }
            }
        }
        return intersections % 2 != 0;
    }
}
