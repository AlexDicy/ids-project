package it.unicam.cs.ids.controller.dto;

import it.unicam.cs.ids.model.Coordinate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record TimedPOIDTO(@NotBlank String name, @NotBlank String description, @NotNull Coordinate coordinate,
                          @Size(min = 7, max = 7) LocalTime[] openingTime,
                          @Size(min = 7, max = 7) LocalTime[] closingTime) {
}
