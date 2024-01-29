package it.unicam.cs.ids.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "municipalities")
public record Municipality(@Id String id, String name, List<Coordinate> perimeter) {
    /**
     * @param name      name of the municipality
     * @param perimeter list of coordinates that define the perimeter of the municipality
     * @throws IllegalArgumentException if the list of coordinates is not valid: it must have at least 3 coordinates
     */
    public Municipality {
        if (perimeter.size() < 3) {
            throw new IllegalArgumentException("A municipality must have at least 3 coordinates");
        }
    }
}
