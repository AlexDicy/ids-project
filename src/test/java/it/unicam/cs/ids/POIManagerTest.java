package it.unicam.cs.ids;

import it.unicam.cs.ids.model.Coordinate;
import it.unicam.cs.ids.model.Municipality;
import it.unicam.cs.ids.model.POI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class POIManagerTest {

    List<POI> list = new ArrayList<>();
    Municipality municipality;

    @BeforeEach
    void setup() {
        list.clear();
        list.add(new POI("ID_1", "POI_1", "First POI", "", false, new Coordinate(88, 180)));
        list.add(new POI("ID_2", "POI_2", "Second POI", "", false, new Coordinate(70, 150)));
        list.add(new POI("ID_3", "POI_3", "Third POI", "", false, new Coordinate(68, 178)));
        list.add(new POI("ID_4", "POI_4", "Fourth POI", "", false, new Coordinate(90, 180)));
        List<Coordinate> perimeter = new ArrayList<>();
        perimeter.add(new Coordinate(10, 10));
        perimeter.add(new Coordinate(30, 100));
        perimeter.add(new Coordinate(20, 140));
        perimeter.add(new Coordinate(120, 170));
        perimeter.add(new Coordinate(120, 10));
        perimeter.add(new Coordinate(10, 10));
        municipality = new Municipality("TestMunicipality", perimeter);
    }

    @Test
    void shouldGet() {
        POIManager manager = new POIManager(municipality);
        manager.poiList = list;
        assertEquals(list.get(0), manager.get("ID_1"));
        assertNotEquals(list.get(2), manager.get("ID_11"));
        assertNull(manager.get("ID_9"));
        assertEquals(list.get(1), manager.get("ID_2"));
        assertEquals(list.get(2), manager.get("ID_3"));
        assertEquals(list.get(3), manager.get("ID_4"));
    }

    @Test
    void shouldSubmit() {
        POIManager manager = new POIManager(municipality);
        manager.poiList = list;
        assertEquals(4, manager.getAll().size());
        POI p = new POI("ID_5", "POI_5", "Fifth POI", "", false, new Coordinate(90, 180));
        manager.submit(p);
        assertEquals(5, manager.getAll().size());
        assertEquals(p, manager.get("ID_5"));
    }

    @Test
    void shouldGetAll() {
        POIManager manager = new POIManager(municipality);
        manager.poiList = list;
        assertEquals(4, manager.getAll().size());
    }

    @Test
    void shouldGetInRange() {
        POIManager manager = new POIManager(municipality);
        manager.poiList = list;
        List<POI> retrieved = manager.getInRange(new Coordinate(60, 100), new Coordinate(80, 179));
        assertEquals(2, retrieved.size());
        assertEquals(list.get(1), retrieved.get(0));
        assertEquals(list.get(2), retrieved.get(1));
    }

    @Test
    void shouldFind() {
        POIManager manager = new POIManager(municipality);
        manager.poiList = list;
        List<POI> retrieved = manager.find("Third");
        assertEquals(1, retrieved.size());
        assertEquals(list.get(2), retrieved.get(0));
    }

    @Test
    void shouldApprove() {
        POIManager manager = new POIManager(municipality);
        manager.poiList = list;
        assertFalse(manager.get("ID_1").isApproved());
        assertFalse(manager.get("ID_2").isApproved());
        manager.approve("ID_1");
        assertTrue(manager.get("ID_1").isApproved());
        assertFalse(manager.get("ID_2").isApproved());
    }

    @Test
    void shouldRemove() {
        POIManager manager = new POIManager(municipality);
        manager.poiList = list;
        assertEquals(4, manager.getAll().size());
        manager.remove("ID_1");
        assertEquals(3, manager.getAll().size());
        assertNull(manager.get("ID_1"));
    }

    @Test
    void shouldGetContentToApprove() {
        POIManager manager = new POIManager(municipality);
        manager.poiList = list;
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
        POIManager manager = new POIManager(municipality);
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
        POIManager manager = new POIManager(municipality);
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
}
