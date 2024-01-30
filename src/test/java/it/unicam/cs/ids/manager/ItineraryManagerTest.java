package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.Coordinate;
import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.model.content.POI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItineraryManagerTest {

    List<Itinerary> list = new ArrayList<>();

    @BeforeEach
    void setup() {
        list.clear();
        List<POI> itineraryList1 = new ArrayList<>();
        itineraryList1.add(POIManagerTest.createPOI("POI_ID_1", "POI_1", "First POI", "", false, new Date(), new Coordinate(90, 180)));
        POI poi2 = POIManagerTest.createPOI("POI_ID_2", "POI_2", "Second POI", "", false, new Date(), new Coordinate(90, 180));
        itineraryList1.add(poi2);
        List<POI> itineraryList2 = new ArrayList<>();
        itineraryList2.add(POIManagerTest.createPOI("POI_ID_3", "POI_3", "Third POI", "", false, new Date(), new Coordinate(90, 180)));
        itineraryList2.add(POIManagerTest.createPOI("POI_ID_4", "POI_4", "Fourth POI", "", false, new Date(), new Coordinate(90, 180)));
        itineraryList2.add(poi2);
        list.add(createItinerary("ID_1", "ITIN_1", "Itinerary 1", "", false, new Date(), itineraryList1));
        list.add(createItinerary("ID_2", "ITIN_2", "Itinerary 2", "", false, new Date(), itineraryList2));
    }

    protected static Itinerary createItinerary(String id, String name, String description, String createdBy, boolean approved, Date date, List<POI> poiList) {
        return new Itinerary( name, description, null, approved, poiList.stream().map(POI::getId).toList());
    }

    @Test
    void shouldGet() {
        ItineraryManager manager = new ItineraryManager(null);
        assertEquals(list.get(0), manager.get("ID_1"));
        assertNotEquals(list.get(0), manager.get("ID_11"));
        assertNull(manager.get("ID_9"));
        assertEquals(list.get(1), manager.get("ID_2"));

        Itinerary it1 = manager.get("ID_1");
        assertEquals("ID_1", it1.getId());
        List<String> poiList = it1.getPoiList();
        assertEquals(2, poiList.size());
        assertEquals("POI_ID_1", poiList.get(0));
        assertEquals("POI_ID_2", poiList.get(1));
    }

    @Test
    void shouldSubmit() {
        ItineraryManager manager = new ItineraryManager(null);
        assertEquals(2, manager.getAll().size());
        POI p = POIManagerTest.createPOI("ID_5", "POI_5", "Fifth POI", "", false, new Date(), new Coordinate(90, 180));
        Itinerary it = createItinerary("ID_5", "ITIN_5", "Itinerary 5", "", false, new Date(), new ArrayList<>());
        it.addPoi(p.getId());
        manager.submit(it);
        assertEquals(3, manager.getAll().size());
        assertEquals(it, manager.get("ID_5"));
    }

    @Test
    void shouldSubmitList() {
        ItineraryManager manager = new ItineraryManager(null);
        assertEquals(2, manager.getAll().size());
        POI p = POIManagerTest.createPOI("ID_5", "POI_5", "Fifth POI", "", false, new Date(), new Coordinate(90, 180));
        POI p2 = POIManagerTest.createPOI("ID_6", "POI_6", "Sixth POI", "", false, new Date(), new Coordinate(90, 180));
        Itinerary it = createItinerary("ID_5", "ITIN_5", "Itinerary 5", "", false, new Date(), new ArrayList<>());
        Itinerary it2 = createItinerary("ID_6", "ITIN_6", "Itinerary 6", "", false, new Date(), new ArrayList<>());
        it.addPoi(p.getId());
        it2.addPoi(p2.getId());
        List<Itinerary> itineraries = new ArrayList<>();
        itineraries.addAll(List.of(it, it2));
        manager.submit(itineraries);
        assertEquals(4, manager.getAll().size());
        assertEquals(it, manager.get("ID_5"));
        assertEquals(it2, manager.get("ID_6"));
    }

    @Test
    void shouldGetAll() {
        ItineraryManager manager = new ItineraryManager(null);
        assertEquals(2, manager.getAll().size());
    }

    @Test
    void shouldFind() {
        ItineraryManager manager = new ItineraryManager(null);
        List<Itinerary> retrieved = manager.find("Itinerary 2");
        assertEquals(1, retrieved.size());
        assertEquals(list.get(1), retrieved.get(0));
    }

    @Test
    void shouldApprove() {
        ItineraryManager manager = new ItineraryManager(null);
        assertFalse(manager.get("ID_1").isApproved());
        assertFalse(manager.get("ID_2").isApproved());
        manager.approve("ID_1");
        assertTrue(manager.get("ID_1").isApproved());
        assertFalse(manager.get("ID_2").isApproved());
    }

    @Test
    void shouldRemove() {
        ItineraryManager manager = new ItineraryManager(null);
        assertEquals(2, manager.getAll().size());
        manager.remove("ID_1");
        assertEquals(1, manager.getAll().size());
        assertNull(manager.get("ID_1"));
    }

    @Test
    void shouldGetContentToApprove() {
        ItineraryManager manager = new ItineraryManager(null);
        assertEquals(2, manager.getAll().size());
        assertEquals(2, manager.getContentToApprove().size());

        manager.approve("ID_1");
        List<Itinerary> contentToApprove = manager.getContentToApprove();
        assertEquals(1, contentToApprove.size());
        assertEquals(list.get(1), contentToApprove.get(0));

        manager.approve("ID_2");
        assertEquals(0, manager.getContentToApprove().size());
    }

    @Test
    void shouldReturnContentsBetweenDates() {
        ItineraryManager manager = new ItineraryManager(null);
        Date today = new Date();
        Date yesterday = new Date(today.getTime() - 24 * 60 * 60 * 1000);
        Date tomorrow = new Date(today.getTime() + 24 * 60 * 60 * 1000);
        Itinerary it1 = createItinerary("ID_1", "ITIN_1", "Itinerary 1", "", false, new Date(today.getTime() - 48 * 60 * 60 * 1000), new ArrayList<>());
        Itinerary it2 = createItinerary("ID_2", "ITIN_2", "Itinerary 2", "", false, yesterday, new ArrayList<>());
        Itinerary it3 = createItinerary("ID_3", "ITIN_3", "Itinerary 3", "", false, new Date(today.getTime() - 10 * 60 * 60 * 1000), new ArrayList<>());
        Itinerary it4 = createItinerary("ID_4", "ITIN_4", "Itinerary 4", "", false, today, new ArrayList<>());
        Itinerary it5 = createItinerary("ID_5", "ITIN_5", "Itinerary 5", "", false, new Date(today.getTime() + 10 * 60 * 60 * 1000), new ArrayList<>());
        Itinerary it6 = createItinerary("ID_6", "ITIN_6", "Itinerary 6", "", false, tomorrow, new ArrayList<>());
        Itinerary it7 = createItinerary("ID_5", "ITIN_5", "Itinerary 7", "", false, new Date(today.getTime() + 48 * 60 * 60 * 1000), new ArrayList<>());
        manager.submit(List.of(it1, it2, it3, it4, it5, it6, it7));

        assertEquals(7, manager.getAll().size());
        assertEquals(3, manager.getInDateRange(yesterday, tomorrow).size());
        assertEquals(1, manager.getInDateRange(today, tomorrow).size());
        assertEquals(1, manager.getInDateRange(new Date(today.getTime() - 11 * 60 * 60 * 1000), today).size());
        assertEquals(3, manager.getInDateRange(new Date(today.getTime() - 11 * 60 * 60 * 1000), new Date(today.getTime() + 11 * 60 * 60 * 1000)).size());
        assertEquals(2, manager.getInDateRange(today, new Date(tomorrow.getTime() + 60 * 60 * 1000)).size());
        assertEquals(0, manager.getInDateRange(new Date(today.getTime() - 48 * 60 * 60 * 1000), yesterday).size());
//        System.out.println(tomorrow);
//        assertEquals(0, manager.getInDateRange(tomorrow, new Date(tomorrow.getTime() + 48 * 60 * 60 * 1000)).size());

    }
}
