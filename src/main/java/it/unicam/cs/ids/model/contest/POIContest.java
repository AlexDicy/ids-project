package it.unicam.cs.ids.model.contest;

import java.util.Date;
import java.util.Objects;

public class POIContest extends ConcreteContest {

    private final String idPOI;

    public POIContest(String id, String name, String description, Date startDate, Date endDate, String createdBy, String idPOI) {
        super(id, name, description, startDate, endDate, createdBy);
        this.idPOI = Objects.requireNonNull(idPOI);
    }

    public String getIdPOI() {
        return idPOI;
    }
}
