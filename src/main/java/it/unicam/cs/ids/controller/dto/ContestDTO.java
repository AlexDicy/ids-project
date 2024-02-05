package it.unicam.cs.ids.controller.dto;

import it.unicam.cs.ids.model.contest.ContestType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ContestDTO(
        @NotBlank String name,
        @NotBlank String description,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) /*@PastOrPresent*/ LocalDateTime startDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) /*@Future*/ LocalDateTime endDate,
        @NotNull ContestType type,
        @NotBlank String createdBy,
        @NotNull List<String> allowedUsers,
        String idPOI
){}
