package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.controller.dto.SearchDTO;
import it.unicam.cs.ids.manager.ItineraryManager;
import it.unicam.cs.ids.manager.POIManager;
import it.unicam.cs.ids.model.content.Content;
import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.model.content.POI;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/search")
public class SearchController {
    private final POIManager poiManager;
    private final ItineraryManager itineraryManager;

    public SearchController(POIManager poiManager, ItineraryManager itineraryManager) {
        this.poiManager = poiManager;
        this.itineraryManager = itineraryManager;
    }


    @PostMapping("")
    public List<Content> search(@RequestBody @Valid SearchDTO dto) {
        String query = dto.query();
        List<Content> contents = new ArrayList<>();
        List<POI> pois = poiManager.find(query);
        List<Itinerary> itineraries = itineraryManager.find(query);
        contents.addAll(pois);
        contents.addAll(itineraries);
        contents.sort((c1, c2) -> {
            if (Objects.equals(c1.getSearchScore(), c2.getSearchScore())) {
                return 0;
            }
            return c1.getSearchScore() > c2.getSearchScore() ? -1 : 1;
        });
        return contents;
    }
}
