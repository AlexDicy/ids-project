package it.unicam.cs.ids.controller.dto;

import it.unicam.cs.ids.model.Coordinate;
import jakarta.validation.constraints.NotNull;

public record GetInAreaDTO(@NotNull Coordinate start, @NotNull Coordinate end) {
}
