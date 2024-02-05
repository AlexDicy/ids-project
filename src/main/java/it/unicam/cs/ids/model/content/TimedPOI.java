package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.Coordinate;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimedPOI extends POI {
    private LocalTime[] openingTimes;
    private LocalTime[] closingTimes;


    public TimedPOI(String name, String description, String createdBy, boolean approved, Coordinate coordinate, LocalTime[] openingTimes, LocalTime[] closingTimes) {
        super(name, description, createdBy, approved, coordinate);
        this.setOpeningTimes(openingTimes);
        this.setClosingTimes(closingTimes);
    }

    public void setOpeningTimes(LocalTime[] openingTimes) {
        if (openingTimes.length != 7) {
            throw new IllegalArgumentException("The opening times must be 7, one for each day of the week");
        }
        this.openingTimes = openingTimes;
    }

    public void setClosingTimes(LocalTime[] closingTimes) {
        if (closingTimes.length != 7) {
            throw new IllegalArgumentException("The closing times must be 7, one for each day of the week");
        }
        this.closingTimes = closingTimes;
    }
}
