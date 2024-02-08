package it.unicam.cs.ids.model.contest;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContentContest.class, name = "CONTENT_CONTEST"),
        @JsonSubTypes.Type(value = POIContest.class, name = "POI_CONTEST")
})
@Document(collection = "contests")
public interface Contest {

    String getId();

    String getName();

    String getDescription();

    LocalDateTime getStartDate();

    LocalDateTime getEndDate();

    List<String> getAllowedUsers();

    List<String> getWinners();

    void setAllowedUsers(List<String> users);

    void setWinners(List<String> users);

    String getAnimatorId();

    ContestType getType();
}
