package it.unicam.cs.ids;

import it.unicam.cs.ids.model.Coordinate;
import it.unicam.cs.ids.model.Image;
import it.unicam.cs.ids.model.POI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImageManagerTest {

    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private POI poi1 = new POI("ID_1", "POI_1", "First POI", "", false, formatter.parse("01/01/2020"), new Coordinate(88, 180));
    private POI poi2 = new POI("ID_2", "POI_2", "Second POI", "", false, formatter.parse("01/01/2020"), new Coordinate(70, 150));

    List<Image> images1;
    List<Image> images2;

    public ImageManagerTest() throws ParseException {
    }

    @BeforeEach
    void setup() throws ParseException {
        images1 = new ArrayList<>();
        images1.add(new Image("ID_1", "Image_1", "First Image", "Author_1", formatter.parse("01/01/2020"), false, "ID_1"));
        images1.add(new Image("ID_2", "Image_2", "Second Image", "Author_2", formatter.parse("02/02/2020"), false, "ID_1"));
        images1.add(new Image("ID_3", "Image_3", "Third Image XY", "Author_3", formatter.parse("03/03/2020"), false, "ID_1"));
        images1.add(new Image("ID_4", "Image_4", "Fourth Image", "Author_4", formatter.parse("04/04/2020"), false, "ID_1"));
        images1.add(new Image("ID_5", "Image_5", "Fifth Image", "Author_5", formatter.parse("05/05/2020"), false, "ID_1"));
        images2 = new ArrayList<>();
        images2.add(new Image("ID_6", "Image_6", "Sixth Image", "Author_6", formatter.parse("01/01/1995"), false, "ID_2"));
        images2.add(new Image("ID_7", "Image_7", "Seventh Image XYZ", "Author_7", formatter.parse("01/01/2007"), false, "ID_2"));
        images2.add(new Image("ID_8", "Image_8", "Eighth Image", "Author_8", formatter.parse("01/01/2019"), false, "ID_2"));
        images2.add(new Image("ID_9", "Image_9", "Ninth Image", "Author_9", formatter.parse("01/01/2020"), false, "ID_2"));
        images2.add(new Image("ID_10", "Image_10", "Tenth Image", "Author_10", formatter.parse("01/01/2021"), false, "ID_2"));
    }

    private ImageManager getImageManager() {
        ImageManager manager = new ImageManager();
        manager.imagesByPoi.put("ID_1", new ArrayList<>(images1));
        manager.imagesByPoi.put("ID_2", new ArrayList<>(images2));
        manager.images.addAll(images1);
        manager.images.addAll(images2);
        return manager;
    }

    @Test
    void shouldGet() {
        ImageManager manager = getImageManager();
        assertEquals(images1.get(0), manager.get("ID_1"));
        assertEquals(images1.get(1), manager.get("ID_2"));
        assertEquals(images1.get(2), manager.get("ID_3"));
        assertEquals(images1.get(3), manager.get("ID_4"));
        assertEquals(images1.get(4), manager.get("ID_5"));
        assertEquals(images2.get(0), manager.get("ID_6"));
        assertEquals(images2.get(1), manager.get("ID_7"));
        assertEquals(images2.get(2), manager.get("ID_8"));
        assertEquals(images2.get(3), manager.get("ID_9"));
        assertEquals(images2.get(4), manager.get("ID_10"));
    }

    @Test
    void shouldSubmit() {
        ImageManager manager = getImageManager();
        assertEquals(10, manager.getAll().size());
        assertNull(manager.get("ID_11"));
        manager.submit(new Image("ID_11", "Image_11", "Eleventh Image", "Author_11", new Date(), false, "ID_1"));
        assertEquals(11, manager.getAll().size());
        assertEquals("ID_11", manager.get("ID_11").getId());
    }

    @Test
    void shouldGetAll() {
        ImageManager manager = getImageManager();
        assertEquals(10, manager.getAll().size());
        assertEquals(images1.get(0), manager.getAll().get(0));
        assertEquals(images1.get(1), manager.getAll().get(1));
        assertEquals(images1.get(2), manager.getAll().get(2));
        assertEquals(images1.get(3), manager.getAll().get(3));
        assertEquals(images1.get(4), manager.getAll().get(4));
        assertEquals(images2.get(0), manager.getAll().get(5));
        assertEquals(images2.get(1), manager.getAll().get(6));
        assertEquals(images2.get(2), manager.getAll().get(7));
        assertEquals(images2.get(3), manager.getAll().get(8));
        assertEquals(images2.get(4), manager.getAll().get(9));
    }

    @Test
    void shouldFind() {
        ImageManager manager = getImageManager();
        assertEquals(10, manager.getAll().size());
        assertEquals(10, manager.find("").size());
        assertEquals(10, manager.find("Image").size());
        assertEquals(1, manager.find("First").size());
        assertEquals(2, manager.find("XY").size());
        assertEquals(1, manager.find("XYZ").size());
    }

    @Test
    void shouldApprove() {
        ImageManager manager = getImageManager();
        assertFalse(manager.get("ID_1").isApproved());
        assertFalse(manager.get("ID_2").isApproved());
        manager.approve("ID_1");
        assertTrue(manager.get("ID_1").isApproved());
        assertFalse(manager.get("ID_2").isApproved());
        manager.approve("ID_2");
        assertTrue(manager.get("ID_1").isApproved());
        assertTrue(manager.get("ID_2").isApproved());
        assertFalse(manager.get("ID_3").isApproved());
    }

    @Test
    void shouldRemove() {
        ImageManager manager = getImageManager();
        assertEquals(10, manager.getAll().size());
        assertNotNull(manager.get("ID_1"));
        manager.remove("ID_1");
        assertEquals(9, manager.getAll().size());
        assertNull(manager.get("ID_1"));
        assertNotNull(manager.get("ID_2"));
    }

    @Test
    void shouldGetContentToApprove() {
        ImageManager manager = getImageManager();
        assertEquals(10, manager.getAll().size());
        assertEquals(10, manager.getContentToApprove().size());
        manager.approve("ID_1");
        assertEquals(9, manager.getContentToApprove().size());
        manager.approve("ID_2");
        assertEquals(8, manager.getContentToApprove().size());
    }

    @Test
    void shouldGetPoiImages() {
        ImageManager manager = getImageManager();
        assertEquals(10, manager.getAll().size());
        assertEquals(5, manager.getPoiImages(poi1.getId()).size());
        assertEquals(5, manager.getPoiImages(poi2.getId()).size());
        manager.remove("ID_1");
        assertEquals(4, manager.getPoiImages(poi1.getId()).size());
        assertEquals(5, manager.getPoiImages(poi2.getId()).size());
        manager.remove("ID_6");
        assertEquals(4, manager.getPoiImages(poi1.getId()).size());
        assertEquals(4, manager.getPoiImages(poi2.getId()).size());
        assertEquals(images1.get(1), manager.getPoiImages(poi1.getId()).get(0));
        assertEquals(images1.get(2), manager.getPoiImages(poi1.getId()).get(1));
        assertEquals(images1.get(3), manager.getPoiImages(poi1.getId()).get(2));
        assertEquals(images1.get(4), manager.getPoiImages(poi1.getId()).get(3));
        assertEquals(images2.get(1), manager.getPoiImages(poi2.getId()).get(0));
        assertEquals(images2.get(2), manager.getPoiImages(poi2.getId()).get(1));
        assertEquals(images2.get(3), manager.getPoiImages(poi2.getId()).get(2));
        assertEquals(images2.get(4), manager.getPoiImages(poi2.getId()).get(3));
    }

    @Test
    void shouldGetPoiImagesInDateRange() throws ParseException {
        ImageManager manager = getImageManager();
        assertEquals(5, manager.getPoiImagesInDateRange(poi1.getId(), formatter.parse("01/01/1990"), formatter.parse("01/01/2021")).size());
        assertEquals(4, manager.getPoiImagesInDateRange(poi2.getId(), formatter.parse("01/01/1990"), formatter.parse("01/01/2021")).size());
        assertEquals(1, manager.getPoiImagesInDateRange(poi1.getId(), formatter.parse("01/01/1990"), formatter.parse("02/01/2020")).size());
        assertEquals(3, manager.getPoiImagesInDateRange(poi2.getId(), formatter.parse("01/01/1990"), formatter.parse("01/01/2020")).size());
        assertEquals(0, manager.getPoiImagesInDateRange(poi1.getId(), formatter.parse("02/01/2021"), formatter.parse("01/01/2022")).size());
        List<Image> images = manager.getPoiImagesInDateRange(poi1.getId(), formatter.parse("31/12/2019"), formatter.parse("02/02/2020"));
        assertEquals(1, images.size());
        assertEquals(images1.get(0), images.get(0));
    }
}
