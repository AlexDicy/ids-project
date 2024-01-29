package it.unicam.cs.ids.model.contest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ConcreteContest implements Contest {

    private final String id;

    private final String name;

    private final String description;

    private final Date startDate;

    private final Date endDate;
    private final String createdBy;
    private List<String> allowedUsers;
    private List<String> winners;

    public ConcreteContest(String id, String name, String description, Date startDate, Date endDate, String createdBy) {
        this.id = Objects.requireNonNull(id, "Contest ID is null");
        this.name = Objects.requireNonNull(name, "Contest name is null");
        this.description = Objects.requireNonNull(description, "Contest description is null");
        this.startDate = Objects.requireNonNull(startDate, "Start date is null");
        this.endDate = Objects.requireNonNull(endDate, "End date is null");
        this.createdBy = Objects.requireNonNull(createdBy, "Creator ID is null");
        allowedUsers = new ArrayList<>();
        winners = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
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
    public Contest getDetails() {
        return this;
    }

    @Override
    public String getAnimatorId() {
        return createdBy;
    }
}
