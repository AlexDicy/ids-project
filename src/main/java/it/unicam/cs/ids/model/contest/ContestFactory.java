package it.unicam.cs.ids.model.contest;

import it.unicam.cs.ids.controller.dto.ContestDTO;
import it.unicam.cs.ids.util.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContestFactory {

    public Contest createContest(ContestDTO dto) {
        switch (dto.type()) {
            case CONTENT_CONTEST -> {
                return createContentContest(dto);
            }
            case POI_CONTEST -> {
                return createPOIContest(dto);
            }
            default -> throw new IllegalArgumentException("Contest type not supported");
        }
    }

    private Contest createContentContest(ContestDTO dto) {
        return new ContentContest(dto.name(), dto.description(), dto.startDate(), dto.endDate(), dto.createdBy(), dto.allowedUsers());
    }

    private Contest createPOIContest(ContestDTO dto) {
        if (dto.idPOI() == null) {
            throw new BadRequestException("POI id is required");
        }
        return new POIContest(dto.name(), dto.description(), dto.startDate(), dto.endDate(), dto.createdBy(), dto.allowedUsers(), dto.idPOI());
    }
}
