package it.unicam.cs.ids.model.contest;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
public interface Contest {

    String getId();

    LocalDateTime getStartDate();

    LocalDateTime getEndDate();

    List<String> getAllowedUsers();

    List<String> getWinners();

    void setAllowedUsers(List<String> users);

    void setWinners(List<String> users);

    String getAnimatorId();

    ContestType getType();
}
