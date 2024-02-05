package it.unicam.cs.ids.controller.dto;

import it.unicam.cs.ids.model.Coordinate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record POIDTO(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull Coordinate coordinate,
        @NotBlank String createdBy
) {}
