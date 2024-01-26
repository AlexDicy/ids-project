package it.unicam.cs.ids;

import it.unicam.cs.ids.model.Image;

import java.util.*;
import java.util.stream.Collectors;

public class ImageManager implements ContentManager<Image> {
    /**
     * The list of all the images indexed by POI id.
     */
    protected Map<String, List<Image>> imagesByPoi = new HashMap<>();

    /**
     * The list of all the images.
     */
    protected List<Image> images = new ArrayList<>();

    @Override
    public Image get(String id) {
        Objects.requireNonNull(id, "Id is not valid");
        for (Image element : images) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public void submit(Image content) {
        Objects.requireNonNull(content, "Invalid image");
        images.add(content);
        if (imagesByPoi.containsKey(content.getIdPoi())) {
            imagesByPoi.get(content.getIdPoi()).add(content);
        } else {
            List<Image> list = new ArrayList<>();
            list.add(content);
            imagesByPoi.put(content.getIdPoi(), list);
        }
    }

    @Override
    public void submit(List<Image> content) {
        Objects.requireNonNull(content, "Content list is empty");
        for (Image i : content) {
            submit(i);
        }
    }

    @Override
    public List<Image> getAll() {
        return new ArrayList<>(images);
    }

    @Override
    public List<Image> getInDateRange(Date start, Date end) {
        return images.stream()
                .filter(image -> image.getCreationDate().after(start) && image.getCreationDate().before(end))
                .collect(Collectors.toList());
    }

    @Override
    public List<Image> find(String query) {
        Objects.requireNonNull(query, "Query is not valid");
        List<Image> matchedImages = new ArrayList<>();
        for (Image element : images) {
            if (element.getName().contains(query) || element.getDescription().contains(query)) {
                matchedImages.add(element);
            }
        }
        return matchedImages;
    }

    @Override
    public void approve(String id) {
        Image imageToApprove = get(id);
        Objects.requireNonNull(imageToApprove, "Image not found");
        imageToApprove.setApproved(true);
    }

    @Override
    public void remove(String id) {
        Image imageToRemove = get(id);
        Objects.requireNonNull(imageToRemove, "Id is not valid");
        imagesByPoi.get(imageToRemove.getIdPoi()).remove(imageToRemove);
        images.remove(imageToRemove);
    }

    @Override
    public List<Image> getContentToApprove() {
        List<Image> imagesToApprove = new ArrayList<>();
        for (Image element : images) {
            if (!element.isApproved()) {
                imagesToApprove.add(element);
            }
        }
        return imagesToApprove;
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
