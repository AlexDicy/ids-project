package it.unicam.cs.ids.controller.dto;

import it.unicam.cs.ids.model.Coordinate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TemporaryPOIDTO(@NotBlank String name, @NotBlank String description, @NotNull Coordinate coordinate, boolean approved,
                              @NotNull LocalDate fromDate,
                              @NotNull LocalDate toDate) {
}
