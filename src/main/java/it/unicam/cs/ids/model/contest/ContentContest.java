package it.unicam.cs.ids.model.contest;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "contests")
public class ContentContest extends GenericContest {

    public ContentContest(String name, String description, LocalDateTime startDate, LocalDateTime endDate, String createdBy, List<String> allowedUsers) {
        super(name, description, startDate, endDate, createdBy, allowedUsers);
    }

    @Override
    public ContestType getType() {
        return ContestType.CONTENT_CONTEST;
    }
}
