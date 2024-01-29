package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.Coordinate;
import it.unicam.cs.ids.model.Municipality;
import it.unicam.cs.ids.model.content.POI;
import it.unicam.cs.ids.repository.POIRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POIManager extends ContentManager<POI, POIRepository> {
    private final Municipality municipality;

    public POIManager(MunicipalityManager municipalityManager, POIRepository repository) {
        super(repository);
        this.municipality = municipalityManager.getDefaultMunicipality();
    }

    public List<POI> getInRange(Coordinate fromCoordinate, Coordinate toCoordinate) {
        if (fromCoordinate.latitude() > toCoordinate.latitude() || fromCoordinate.longitude() > toCoordinate.longitude()) {
            throw new IllegalArgumentException("Invalid latitude or longitude range");
        }

        return repository.findAllByCoordinatesRange(fromCoordinate.latitude(), toCoordinate.latitude(), fromCoordinate.longitude(), toCoordinate.longitude());
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
