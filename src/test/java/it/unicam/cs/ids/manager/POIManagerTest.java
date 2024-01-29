package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.Coordinate;
import it.unicam.cs.ids.model.Municipality;
import it.unicam.cs.ids.model.content.POI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class POIManagerTest {

    List<POI> list = new ArrayList<>();
    Municipality municipality;

    @BeforeEach
    void setup() {
        list.clear();
        list.add(createPOI("ID_1", "POI_1", "First POI", "", false, new Date(), new Coordinate(88, 180)));
        list.add(createPOI("ID_2", "POI_2", "Second POI", "", false, new Date(), new Coordinate(70, 150)));
        list.add(createPOI("ID_3", "POI_3", "Third POI", "", false, new Date(), new Coordinate(68, 178)));
        list.add(createPOI("ID_4", "POI_4", "Fourth POI", "", false, new Date(), new Coordinate(90, 180)));
        List<Coordinate> perimeter = new ArrayList<>();
        perimeter.add(new Coordinate(10, 10));
        perimeter.add(new Coordinate(30, 100));
        perimeter.add(new Coordinate(20, 140));
        perimeter.add(new Coordinate(120, 170));
        perimeter.add(new Coordinate(120, 10));
        perimeter.add(new Coordinate(10, 10));
        municipality = new Municipality("", "TestMunicipality", perimeter);
    }

    protected static POI createPOI(String id, String name, String description, String createdBy, boolean approved, Date date, Coordinate coordinate) {
        return new POI(name, description, null, approved, coordinate);
    }

    @Test
    void shouldGet() {
        POIManager manager = new POIManager(null, null);
        assertEquals(list.get(0), manager.get("ID_1"));
        assertNotEquals(list.get(2), manager.get("ID_11"));
        assertNull(manager.get("ID_9"));
        assertEquals(list.get(1), manager.get("ID_2"));
        assertEquals(list.get(2), manager.get("ID_3"));
        assertEquals(list.get(3), manager.get("ID_4"));
    }

    @Test
    void shouldSubmit() {
        POIManager manager = new POIManager(null, null);
        assertEquals(4, manager.getAll().size());
        POI p = createPOI("ID_5", "POI_5", "Fifth POI", "", false, new Date(), new Coordinate(90, 180));
        manager.submit(p);
        assertEquals(5, manager.getAll().size());
        assertEquals(p, manager.get("ID_5"));
    }

    @Test
    void shouldSubmitAll() {
        POIManager manager = new POIManager(null, null);
        assertEquals(4, manager.getAll().size());
        List<POI> newList = new ArrayList<>();
        newList.add(createPOI("ID_5", "POI_5", "Fifth POI", "", false, new Date(), new Coordinate(90, 180)));
        newList.add(createPOI("ID_6", "POI_6", "Sixth POI", "", false, new Date(), new Coordinate(90, 180)));
        manager.submit(newList);
        assertEquals(6, manager.getAll().size());
        assertEquals(newList.get(0), manager.get("ID_5"));
        assertEquals(newList.get(1), manager.get("ID_6"));
    }

    @Test
    void shouldGetAll() {
        POIManager manager = new POIManager(null, null);
        assertEquals(4, manager.getAll().size());
    }

    @Test
    void shouldGetInRange() {
        POIManager manager = new POIManager(null, null);
        List<POI> retrieved = manager.getInRange(new Coordinate(60, 100), new Coordinate(80, 179));
        assertEquals(2, retrieved.size());
        assertEquals(list.get(1), retrieved.get(0));
        assertEquals(list.get(2), retrieved.get(1));
    }

    @Test
    void shouldFind() {
        POIManager manager = new POIManager(null, null);
        List<POI> retrieved = manager.find("Third");
        assertEquals(1, retrieved.size());
        assertEquals(list.get(2), retrieved.get(0));
    }

    @Test
    void shouldApprove() {
        POIManager manager = new POIManager(null, null);
        assertFalse(manager.get("ID_1").isApproved());
        assertFalse(manager.get("ID_2").isApproved());
        manager.approve("ID_1");
        assertTrue(manager.get("ID_1").isApproved());
        assertFalse(manager.get("ID_2").isApproved());
    }

    @Test
    void shouldRemove() {
        POIManager manager = new POIManager(null, null);
        assertEquals(4, manager.getAll().size());
        manager.remove("ID_1");
        assertEquals(3, manager.getAll().size());
        assertNull(manager.get("ID_1"));
    }

    @Test
    void shouldGetContentToApprove() {
        POIManager manager = new POIManager(null, null);
        assertEquals(4, manager.getAll().size());
        assertEquals(4, manager.getContentToApprove().size());
        manager.approve("ID_1");
        assertEquals(3, manager.getContentToApprove().size());
        manager.approve("ID_2");
        List<POI> contentToApprove = manager.getContentToApprove();
        assertEquals(2, contentToApprove.size());
        assertEquals(list.get(2), contentToApprove.get(0));
        assertEquals(list.get(3), contentToApprove.get(1));
        manager.approve("ID_3");
        assertEquals(1, manager.getContentToApprove().size());
        manager.approve("ID_4");
        assertEquals(0, manager.getContentToApprove().size());
    }

    @Test
    void checkCoordinateIsValid() {
        POIManager manager = new POIManager(null, null);
        assertTrue(manager.checkCoordinate(new Coordinate(40, 20)));
        assertTrue(manager.checkCoordinate(new Coordinate(30, 100)));
        assertTrue(manager.checkCoordinate(new Coordinate(15, 15)));
        assertTrue(manager.checkCoordinate(new Coordinate(110, 15)));
        assertTrue(manager.checkCoordinate(new Coordinate(30, 102)));
        assertTrue(manager.checkCoordinate(new Coordinate(46, 126)));
        assertTrue(manager.checkCoordinate(new Coordinate(28, 85)));
        assertTrue(manager.checkCoordinate(new Coordinate(110, 160)));
    }

    @Test
    void checkCoordinateIsNotValid() {
        POIManager manager = new POIManager(null, null);
        // on the perimeter, is not considered inside
        assertFalse(manager.checkCoordinate(new Coordinate(10, 10)));
        assertFalse(manager.checkCoordinate(new Coordinate(20, 140)));
        assertFalse(manager.checkCoordinate(new Coordinate(120, 170)));
        assertFalse(manager.checkCoordinate(new Coordinate(120, 10)));
        assertFalse(manager.checkCoordinate(new Coordinate(10, 11)));

        // outside the perimeter
        assertFalse(manager.checkCoordinate(new Coordinate(25, 100)));
        assertFalse(manager.checkCoordinate(new Coordinate(20, 130)));
        assertFalse(manager.checkCoordinate(new Coordinate(110, 170)));
        assertFalse(manager.checkCoordinate(new Coordinate(25, 98)));
        assertFalse(manager.checkCoordinate(new Coordinate(20, 141)));
        assertFalse(manager.checkCoordinate(new Coordinate(121, 170)));
        assertFalse(manager.checkCoordinate(new Coordinate(120, 11)));
        assertFalse(manager.checkCoordinate(new Coordinate(9, 10)));
        assertFalse(manager.checkCoordinate(new Coordinate(29, 100)));
        assertFalse(manager.checkCoordinate(new Coordinate(20, 139)));
        assertFalse(manager.checkCoordinate(new Coordinate(119, 170)));
        assertFalse(manager.checkCoordinate(new Coordinate(120, 9)));
    }

    @Test
    void shouldGetInDateRange() {
        POIManager manager = new POIManager(null, null);
        Date today = new Date();
        Date yesterday = new Date(today.getTime() - 86400000L);
        Date tomorrow = new Date(today.getTime() + 86400000L);
        POI poi1 = createPOI("ID_1", "POI_1", "First POI", "", false, yesterday, new Coordinate(88, 180));
        POI poi2 = createPOI("ID_2", "POI_2", "Second POI", "", false, new Date(today.getTime() - 23 * 60 * 60 * 1000), new Coordinate(70, 150));
        POI poi3 = createPOI("ID_2", "POI_2", "Second POI", "", false, today, new Coordinate(70, 150));
        POI poi4 = createPOI("ID_3", "POI_3", "Third POI", "", false, new Date(today.getTime() + 23 * 60 * 60 * 1000), new Coordinate(68, 178));
        POI poi5 = createPOI("ID_3", "POI_3", "Third POI", "", false, tomorrow, new Coordinate(68, 178));
        //manager.poiList.addAll(List.of(poi1, poi2, poi3, poi4, poi5));
        assertEquals(5, manager.getAll().size());
        assertEquals(1, manager.getInDateRange(yesterday, today).size());
        assertEquals(1, manager.getInDateRange(today, tomorrow).size());
        assertEquals(3, manager.getInDateRange(yesterday, tomorrow).size());
        assertEquals(2, manager.getInDateRange(yesterday, new Date(today.getTime() + 60 * 60 * 1000)).size());
        assertEquals(2, manager.getInDateRange(new Date(today.getTime() - 60 * 60 * 1000), tomorrow).size());
    }
}
