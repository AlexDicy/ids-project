package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.content.POI;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface POIRepository extends MongoRepository<POI, String> {

    List<POI> findAllBy(TextQuery textQuery);

    List<POI> findAllByApproved(boolean approved);

    List<POI> findAllByCreationDateBetween(Date start, Date end);
}
