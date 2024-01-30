package it.unicam.cs.ids.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ItineraryDTO(@NotBlank String name, @NotBlank String description, @NotEmpty @Size(min = 3) String[] pois) {
}
