package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.controller.dto.*;
import it.unicam.cs.ids.manager.POIManager;
import it.unicam.cs.ids.model.content.POI;
import it.unicam.cs.ids.model.content.TemporaryPOI;
import it.unicam.cs.ids.model.content.TimedPOI;
import it.unicam.cs.ids.util.CoordinateOutOfPerimeterException;
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
            throw new CoordinateOutOfPerimeterException("Coordinate is not valid");
        }
        manager.submit(new POI(poi.name(), poi.description(), poi.createdBy(), poi.approved(), poi.coordinate()));
    }

    @PostMapping("/submitTimed")
    public void submit(@RequestBody @Valid TimedPOIDTO poi) {
        if (!manager.checkCoordinate(poi.coordinate())) {
            throw new CoordinateOutOfPerimeterException("Coordinate is not valid");
        }
        manager.submit(new TimedPOI(poi.name(), poi.description(), null, poi.approved(), poi.coordinate(), poi.openingTimes(), poi.closingTimes()));
    }

    @PostMapping("/submitTemporary")
    public void submit(@RequestBody @Valid TemporaryPOIDTO poi) {
        if (!manager.checkCoordinate(poi.coordinate())) {
            throw new CoordinateOutOfPerimeterException("Coordinate is not valid");
        }
        manager.submit(new TemporaryPOI(poi.name(), poi.description(), null, poi.approved(), poi.coordinate(), poi.fromDate(), poi.toDate()));
    }

    @PostMapping("/getInRange")
    public List<POI> getInRange(@RequestBody @Valid GetInAreaDTO dto) {
        return manager.getInRange(dto.start(), dto.end());
    }

    @GetMapping("/approve/{id}")
    public void approve(@PathVariable String id) {
        manager.approve(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        manager.remove(id);
    }

    @PostMapping("/report/{id}/from/{reporterId}")
    public String report(@PathVariable String id, @PathVariable String reporterId, @RequestBody @Valid ReportDTO dto) {
        return manager.report(id, reporterId, dto.reason());
    }
}
