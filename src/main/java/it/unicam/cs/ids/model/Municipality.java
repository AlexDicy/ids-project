package it.unicam.cs.ids.model;

import java.util.List;

public record Municipality(String name, List<Coordinate> perimeter) {
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
