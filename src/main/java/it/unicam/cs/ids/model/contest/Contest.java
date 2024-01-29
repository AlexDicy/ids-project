package it.unicam.cs.ids.model.contest;

import java.util.Date;
import java.util.List;

public interface Contest {

    String getId();

    Date getStartDate();

    Date getEndDate();

    List<String> getAllowedUsers();

    List<String> getWinners();

    void setAllowedUsers(List<String> users);

    void setWinners(List<String> users);

    Contest getDetails();

    String getAnimatorId();
}
