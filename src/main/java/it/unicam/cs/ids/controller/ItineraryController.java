package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.controller.dto.ItineraryDTO;
import it.unicam.cs.ids.manager.ItineraryManager;
import it.unicam.cs.ids.manager.POIManager;
import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.model.content.POI;
import it.unicam.cs.ids.util.BadRequestException;
import it.unicam.cs.ids.util.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/itinerary")
public class ItineraryController {
    private final ItineraryManager manager;
    private final POIManager poiManager;

    public ItineraryController(ItineraryManager manager, POIManager poiManager) {
        this.manager = manager;
        this.poiManager = poiManager;
    }

    @GetMapping("/{id}")
    public Itinerary getById(@PathVariable String id) {
        Itinerary itinerary = manager.get(id);
        if (itinerary == null) {
            throw new NotFoundException("Itinerary not found");
        }
        return itinerary;
    }

    @PostMapping("/submit")
    public void submit(@RequestBody @Valid ItineraryDTO dto) {
        // check if the pois' id in the itinerary are valid
        List<String> ids = List.of(dto.pois());
        List<POI> pois = poiManager.getApproved(ids);
        if (pois.size() != dto.pois().length) {
            throw new BadRequestException("One or more POI not found");
        }

        manager.submit(new Itinerary(dto.name(), dto.description(), null, false, ids));
    }
}
