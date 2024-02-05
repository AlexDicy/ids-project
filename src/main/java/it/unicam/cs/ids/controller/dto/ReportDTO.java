package it.unicam.cs.ids.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ReportDTO(@NotBlank String reason) {
}
