package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.repository.ItineraryRepository;
import org.springframework.stereotype.Service;

@Service
public class ItineraryManager extends ContentManager<Itinerary, ItineraryRepository> {

    public ItineraryManager(ItineraryRepository repository) {
        super(repository);
    }
}
