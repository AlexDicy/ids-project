package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.content.POI;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface POIRepository extends ContentRepository<POI> {
    @Query("{ 'approved': true, 'coordinate.latitude' : { $gte: ?0, $lte: ?1 }, 'coordinate.longitude' : { $gte: ?2, $lte: ?3 } }")
    List<POI> findAllByCoordinatesRange(double minLat, double maxLat, double minLon, double maxLon);
}
