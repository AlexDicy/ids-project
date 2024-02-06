package it.unicam.cs.ids.controller.dto;

import it.unicam.cs.ids.model.Coordinate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetTime;

public record TimedPOIDTO(@NotBlank String name, @NotBlank String description, @NotNull Coordinate coordinate, boolean approved,
                          @Size(min = 7, max = 7) OffsetTime[][] openingTimes,
                          @Size(min = 7, max = 7) OffsetTime[][] closingTimes) {
}
