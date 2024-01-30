package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.manager.ImageManager;
import it.unicam.cs.ids.manager.ItineraryManager;
import it.unicam.cs.ids.manager.POIManager;
import it.unicam.cs.ids.model.content.Content;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/v1/content")
public class ContentController {
    private final ItineraryManager itineraryManager;
    private final POIManager poiManager;
    private final ImageManager imageManager;

    public ContentController(ItineraryManager itineraryManager, POIManager poiManager, ImageManager imageManager) {
        this.itineraryManager = itineraryManager;
        this.poiManager = poiManager;
        this.imageManager = imageManager;
    }

    @GetMapping("/pending")
    public List<Content> getPending() {
        List<Content> contents = new ArrayList<>();
        contents.addAll(itineraryManager.getContentToApprove());
        contents.addAll(poiManager.getContentToApprove());
        contents.addAll(imageManager.getContentToApprove());
        // sort by creation date, oldest first
        contents.sort(Comparator.comparing(Content::getCreationDate));
        return contents;
    }
}
