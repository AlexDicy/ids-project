package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.content.Image;
import it.unicam.cs.ids.repository.ContentReportRepository;
import it.unicam.cs.ids.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImageManager extends ContentManager<Image, ImageRepository> {

    public ImageManager(ImageRepository repository, ContentReportRepository reportRepository) {
        super(repository, reportRepository);
    }

    /**
     * Returns a list of images associated to a POI.
     *
     * @param idPOI the id of the POI.
     */
    public List<Image> getPoiImages(String idPOI) {
        return repository.findAllByIdPoi(idPOI);
    }

    /**
     * Returns a list of images associated to a POI inside a given date range.
     *
     * @param idPOI the id of the POI.
     * @param start the start date.
     * @param end   the end date.
     */
    public List<Image> getPoiImagesInDateRange(String idPOI, Date start, Date end) {
        Objects.requireNonNull(start, "Start date is not valid");
        Objects.requireNonNull(end, "End date is not valid");
        return repository.findAllByIdPoiAndCreationDateBetween(idPOI, start, end);
    }
}
