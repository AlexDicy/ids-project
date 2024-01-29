package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.controller.dto.GetInAreaDTO;
import it.unicam.cs.ids.controller.dto.POIDTO;
import it.unicam.cs.ids.manager.POIManager;
import it.unicam.cs.ids.model.content.POI;
import it.unicam.cs.ids.util.BadRequestException;
import it.unicam.cs.ids.util.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/poi")
public class POIController {
    private final POIManager manager;

    public POIController(POIManager manager) {
        this.manager = manager;
    }

    @GetMapping("/{id}")
    public POI getById(@PathVariable String id) {
        POI poi = manager.get(id);
        if (poi == null) {
            throw new NotFoundException("POI not found");
        }
        return poi;
    }

    @PostMapping("/submit")
    public void submit(@RequestBody @Valid POIDTO poi) {
        if (!manager.checkCoordinate(poi.coordinate())) {
            throw new BadRequestException("Coordinate are not valid");
        }
        manager.submit(new POI(poi.name(), poi.description(), null, false, poi.coordinate()));
    }

    @PostMapping("/getInRange")
    public List<POI> getInRange(@RequestBody @Valid GetInAreaDTO dto) {
        return manager.getInRange(dto.start(), dto.end());
    }
}
