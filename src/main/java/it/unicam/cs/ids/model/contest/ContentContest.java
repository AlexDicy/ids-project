package it.unicam.cs.ids.model.contest;

import java.util.Date;

public class ContentContest extends ConcreteContest {
    public ContentContest(String id, String name, String description, Date startDate, Date endDate, String createdBy) {
        super(id, name, description, startDate, endDate, createdBy);
    }
}
