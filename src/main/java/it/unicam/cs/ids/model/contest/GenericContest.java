package it.unicam.cs.ids.model.contest;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "contests")
public abstract class GenericContest implements Contest {

    @Getter
    @Id
    private String id;

    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final LocalDateTime startDate;

    @Getter
    private final LocalDateTime endDate;
    @CreatedBy
    private final String createdBy;
    private List<String> allowedUsers;
    private List<String> winners;

    public GenericContest(String name, String description, LocalDateTime startDate, LocalDateTime endDate, String createdBy, List<String> allowedUsers) {
        this.name = Objects.requireNonNull(name, "Contest name is null");
        this.description = Objects.requireNonNull(description, "Contest description is null");
        this.startDate = Objects.requireNonNull(startDate, "Start date is null");
        this.endDate = Objects.requireNonNull(endDate, "End date is null");
        this.createdBy = Objects.requireNonNull(createdBy, "Creator ID is null");
        this.allowedUsers = Objects.requireNonNull(allowedUsers, "Users list not valid");
        winners = new ArrayList<>();
    }

    @Override
    public List<String> getAllowedUsers() {
        return allowedUsers;
    }

    @Override
    public List<String> getWinners() {
        return winners;
    }

    @Override
    public void setAllowedUsers(List<String> users) {
        allowedUsers = Objects.requireNonNull(users, "Users list is empty");
    }

    @Override
    public void setWinners(List<String> users) {
        winners = Objects.requireNonNull(users, "Users list is empty");
    }

    @Override
    public String getAnimatorId() {
        return createdBy;
    }
}
