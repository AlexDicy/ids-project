package it.unicam.cs.ids.model.contest;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "contests")
public class POIContest extends GenericContest {

    private final String idPOI;

    public POIContest(String name, String description, LocalDateTime startDate, LocalDateTime endDate,
                      String createdBy, List<String> allowedUsers, String idPOI) {
        super(name, description, startDate, endDate, createdBy, allowedUsers);
        this.idPOI = Objects.requireNonNull(idPOI);
    }

    public String getIdPOI() {
        return idPOI;
    }

    @Override
    public ContestType getType() {
        return ContestType.POI_CONTEST;
    }
}
