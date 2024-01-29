package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.manager.POIManager;
import it.unicam.cs.ids.model.content.POI;
import it.unicam.cs.ids.util.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/poi")
public class POIController {
    private final POIManager manager;

    public POIController(POIManager manager) {
        this.manager = manager;
    }

    @GetMapping("/{id}")
    public POI getPOI(@PathVariable String id) {
        POI poi = manager.get(id);
        if (poi == null) {
            throw new NotFoundException("POI not found");
        }
        return poi;
    }
}
