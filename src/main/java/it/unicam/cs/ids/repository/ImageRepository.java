package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.content.Image;

import java.util.Date;
import java.util.List;

public interface ImageRepository extends ContentRepository<Image> {

    List<Image> findAllByIdPoi(String idPOI);

    List<Image> findAllByIdPoiAndCreationDateBetween(String idPOI, Date start, Date end);
}
