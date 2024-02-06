package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.Coordinate;
import lombok.Getter;

import java.time.OffsetTime;

@Getter
public class TimedPOI extends POI {
    private final OffsetTime[][] openingTimes;
    private final OffsetTime[][] closingTimes;


    public TimedPOI(String name, String description, String createdBy, boolean approved, Coordinate coordinate, OffsetTime[][] openingTimes, OffsetTime[][] closingTimes) {
        super(name, description, createdBy, approved, coordinate);
        // check if opening and closing times are valid
        if (openingTimes.length != 7 || closingTimes.length != 7) {
            throw new IllegalArgumentException("The opening and closing times must be 7 days long");
        }
        for (int i = 0; i < 7; i++) {
            if (openingTimes[i].length != closingTimes[i].length) {
                throw new IllegalArgumentException("The opening and closing times must have the same length");
            }
            for (int j = 0; j < openingTimes[i].length; j++) {
                if (openingTimes[i][j].isAfter(closingTimes[i][j])) {
                    throw new IllegalArgumentException("The opening time must be before the closing time");
                }
            }
        }
        this.openingTimes = openingTimes;
        this.closingTimes = closingTimes;
    }
}
