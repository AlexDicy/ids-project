package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.Coordinate;
import it.unicam.cs.ids.model.Municipality;
import it.unicam.cs.ids.model.content.POI;
import it.unicam.cs.ids.repository.POIRepository;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class POIManager implements ContentManager<POI> {
    private final Municipality municipality;
    private final POIRepository repository;

    public POIManager(MunicipalityManager municipalityManager, POIRepository repository) {
        this.municipality = municipalityManager.getDefaultMunicipality();
        this.repository = repository;
    }

    @Override
    public POI get(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void submit(POI poi) {
        repository.save(poi);
    }

    @Override
    public void submit(List<POI> content) {
        repository.saveAll(content);
    }

    @Override
    public List<POI> getAll() {
        return repository.findAll();
    }

    @Override
    public List<POI> find(String query) {
        TextQuery textQuery = TextQuery.queryText(new TextCriteria().matching(query)).sortByScore();
        return repository.findAllBy(textQuery);
    }

    @Override
    public void approve(String id) {
        POI poiToApprove = get(id);
        Objects.requireNonNull(poiToApprove, "POI not found");
        poiToApprove.setApproved(true);
        repository.save(poiToApprove);
    }

    @Override
    public void remove(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<POI> getContentToApprove() {
        return repository.findAllByApproved(false);
    }

    @Override
    public List<POI> getInDateRange(Date start, Date end) {
        return repository.findAllByCreationDateBetween(start, end);
    }

    public List<POI> getInRange(Coordinate fromCoordinate, Coordinate toCoordinate) {
        if (fromCoordinate.latitude() > toCoordinate.latitude() || fromCoordinate.longitude() > toCoordinate.longitude()) {
            throw new IllegalArgumentException("Invalid latitude or longitude range");
        }

        List<POI> poiList = getAll();
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
