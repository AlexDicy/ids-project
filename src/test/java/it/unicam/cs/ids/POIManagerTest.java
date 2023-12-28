package it.unicam.cs.ids;

import it.unicam.cs.ids.model.POI;
import it.unicam.cs.ids.model.StaticPOI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class POIManagerTest {

    List<POI> list = new ArrayList<>();

    @BeforeEach
    void setup(){
        list.clear();
        list.add(new StaticPOI("ID_1", "POI_1", "First POI", false, 88, 180));
        list.add(new StaticPOI("ID_2", "POI_2", "Second POI", false, 70, 150));
        list.add(new StaticPOI("ID_3", "POI_3", "Third POI", false, 68, 178));
        list.add(new StaticPOI("ID_4", "POI_4", "Fourth POI", false, 90, 180));
    }

    @Test
    void shouldGet(){
        POIManager manager = new POIManager();
        manager.poiList = list;
        assertEquals(list.get(0), manager.get("ID_1"));
        assertNotEquals(list.get(2), manager.get("ID_11"));
        assertNull(manager.get("ID_9"));
        assertEquals(list.get(1), manager.get("ID_2"));
        assertEquals(list.get(2), manager.get("ID_3"));
        assertEquals(list.get(3), manager.get("ID_4"));
    }

    @Test
    void shouldSubmit(){
        POIManager manager = new POIManager();
        manager.poiList = list;
        assertEquals(4, manager.getAll().size());
        POI p = new StaticPOI("ID_5", "POI_5", "Fifth POI", false, 90, 180);
        manager.submit(p);
        assertEquals(5, manager.getAll().size());
        assertEquals(p, manager.get("ID_5"));
    }

    @Test
    void shouldGetAll(){
        POIManager manager = new POIManager();
        manager.poiList = list;
        assertEquals(4, manager.getAll().size());
    }

    @Test
    void shouldGetInRange(){
        POIManager manager = new POIManager();
        manager.poiList = list;
        List<POI> retrieved = manager.getInRange(60, 100, 80, 179);
        assertEquals(2, retrieved.size());
        assertEquals(list.get(1), retrieved.get(0));
        assertEquals(list.get(2), retrieved.get(1));
    }

    @Test
    void shouldFind(){
        POIManager manager = new POIManager();
        manager.poiList = list;
        List<POI> retrieved = manager.find("Third");
        assertEquals(1, retrieved.size());
        assertEquals(list.get(2), retrieved.get(0));
    }
}
