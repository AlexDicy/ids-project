package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.content.Image;
import it.unicam.cs.ids.repository.ImageRepository;

import java.util.*;

public class ImageManager extends ContentManager<Image, ImageRepository> {
    /**
     * The list of all the images indexed by POI id.
     */
    protected Map<String, List<Image>> imagesByPoi = new HashMap<>();

    /**
     * The list of all the images.
     */
    protected List<Image> images = new ArrayList<>();

    public ImageManager(ImageRepository repository) {
        super(repository);
    }

    /**
     * Returns a list of images associated to a POI.
     *
     * @param idPOI the id of the POI.
     */
    public List<Image> getPoiImages(String idPOI) {
        Objects.requireNonNull(idPOI, "Id is not valid");
        return imagesByPoi.get(idPOI);
    }

    /**
     * Returns a list of images associated to a POI inside a given date range.
     *
     * @param idPOI the id of the POI.
     * @param start the start date.
     * @param end   the end date.
     */
    public List<Image> getPoiImagesInDateRange(String idPOI, Date start, Date end) {
        Objects.requireNonNull(idPOI, "Id is not valid");
        Objects.requireNonNull(start, "Start date is not valid");
        Objects.requireNonNull(end, "End date is not valid");
        List<Image> images = imagesByPoi.get(idPOI);
        List<Image> imagesInRange = new ArrayList<>();
        for (Image element : images) {
            if (element.getCreationDate().after(start) && element.getCreationDate().before(end)) {
                imagesInRange.add(element);
            }
        }
        return imagesInRange;
    }
}
