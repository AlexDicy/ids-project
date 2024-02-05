package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.content.Image;
import it.unicam.cs.ids.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ImageManager extends ContentManager<Image, ImageRepository> {

    public ImageManager(ImageRepository repository) {
        super(repository);
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
    public List<Image> getPoiImagesInDateRange(String idPOI, LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(start, "Start date is not valid");
        Objects.requireNonNull(end, "End date is not valid");
        return repository.findAllByIdPoiAndCreationDateBetween(idPOI, start, end);
    }

    public List<Image> getAllowedUsersApprovedPoiImagesInDateRange(String idPOI, LocalDateTime start, LocalDateTime end, List<String> allowedUsers) {
        if ( allowedUsers.isEmpty())
            return repository.findAllApprovedPoiImagesInDateRange(idPOI, start, end);
        return repository.findAllAllowedUsersApprovedPoiImagesInDateRange(idPOI, start, end, allowedUsers);
    }
}
