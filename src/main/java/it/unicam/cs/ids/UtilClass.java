package it.unicam.cs.ids;

import it.unicam.cs.ids.model.Coordinate;
import it.unicam.cs.ids.model.Municipality;

import java.util.ArrayList;
import java.util.List;

public class UtilClass {
    private static ImageManager imageManager;
    private static POIManager poiManager;
    private static ItineraryManager itineraryManager;
    private static Municipality municipality;

    public static ImageManager getImageManager() {
        if (imageManager == null) {
            imageManager = new ImageManager();
        }
        return imageManager;
    }

    public static POIManager getPOIManager() {
        if (poiManager == null) {
            poiManager = new POIManager(getMunicipality());
        }
        return poiManager;
    }

    public static ItineraryManager getItineraryManager() {
        if (itineraryManager == null) {
            itineraryManager = new ItineraryManager();
        }
        return itineraryManager;
    }

    public static Municipality getMunicipality() {
        if (municipality == null) {
            List<Coordinate> perimeter = new ArrayList<>();
            perimeter.add(new Coordinate(10, 10));
            perimeter.add(new Coordinate(30, 100));
            perimeter.add(new Coordinate(20, 140));
            perimeter.add(new Coordinate(120, 170));
            perimeter.add(new Coordinate(120, 10));
            municipality = new Municipality("TestMunicipality", perimeter);
        }
        return municipality;
    }
}
