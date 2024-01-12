package it.unicam.cs.ids;

import it.unicam.cs.ids.model.Itinerary;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItineraryManager implements ContentManager<Itinerary> {
    protected List<Itinerary> itineraryList;
    @Override
    public Itinerary get(String id) {
        Objects.requireNonNull(id, "Id is not valid");
        for (Itinerary element : itineraryList) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public void submit(Itinerary content) {
        Objects.requireNonNull(content, "Invalid itinerary");
        itineraryList.add(content);
    }

    @Override
    public List<Itinerary> getAll() {
        return new ArrayList<>(itineraryList);
    }

    @Override
    public List<Itinerary> find(String query) {
        Objects.requireNonNull(query, "Query is not valid");
        List<Itinerary> itineraries = new ArrayList<>();
        for (Itinerary element : itineraryList) {
            if (element.getName().contains(query) || element.getDescription().contains(query)) {
                itineraries.add(element);
            }
        }
        return itineraries;
    }

    @Override
    public void approve(String id) {
        Itinerary itineraryToApprove = get(id);
        Objects.requireNonNull(itineraryToApprove, "Itinerary not found");
        itineraryToApprove.setApproved(true);
    }

    @Override
    public void remove(String id) {
        Itinerary itineraryToRemove = get(id);
        Objects.requireNonNull(itineraryToRemove, "Id is not valid");
        itineraryList.remove(itineraryToRemove);
    }

    @Override
    public List<Itinerary> getContentToApprove() {
        List<Itinerary> itineraries = new ArrayList<>();
        for (Itinerary element : itineraryList) {
            if (!element.isApproved()) {
                itineraries.add(element);
            }
        }
        return itineraries;
    }
}
