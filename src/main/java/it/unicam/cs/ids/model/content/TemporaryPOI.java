package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.Coordinate;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TemporaryPOI extends POI {
    private final LocalDate fromDate;
    private final LocalDate toDate;

    public TemporaryPOI(String name, String description, String createdBy, boolean approved, Coordinate coordinate, LocalDate fromDate, LocalDate toDate) {
        super(name, description, createdBy, approved, coordinate);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
