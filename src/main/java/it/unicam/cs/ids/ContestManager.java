package it.unicam.cs.ids;

import it.unicam.cs.ids.model.Content;
import it.unicam.cs.ids.model.Contest;
import it.unicam.cs.ids.model.Image;
import it.unicam.cs.ids.model.POIContest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContestManager {

    protected final List<Contest> contestList = new ArrayList<>();

    public Contest get(String id) {
        Objects.requireNonNull(id, "Id is not valid");

        for (Contest c : contestList) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void submit(Contest contest) {
        contestList.add(Objects.requireNonNull(contest, "Contest is not valid"));
    }

    public List<Contest> getCompletedContests(String animatorId) {
        Objects.requireNonNull(animatorId, "Id is not valid");
        List<Contest> contests = new ArrayList<>();

        for (Contest c : contestList) {
            if (c.getAnimatorId().equals(animatorId) && c.getEndDate().before(new Date())) {
                contests.add(c);
            }
        }

        return contests;
    }

    public List<Content> getContentByContest(String contestId, Date startDate, Date endDate) {
        Objects.requireNonNull(startDate, "Start date is not valid");
        Objects.requireNonNull(endDate, "End date is not valid");
        Contest contest = get(Objects.requireNonNull(contestId, "Id is not valid"));

        List<Content> contents = new ArrayList<>();

        if (contest instanceof POIContest poiContest) {
            List<Image> images = UtilClass.getImageManager().getPoiImagesInDateRange(poiContest.getIdPOI(), startDate, endDate);
            contents.addAll(images);
        } else {
            contents.addAll(UtilClass.getItineraryManager().getInDateRange(startDate, endDate));
            contents.addAll(UtilClass.getPOIManager().getInDateRange(startDate, endDate));
        }

        List<String> allowedUsers = contest.getAllowedUsers();

        return contents.stream()
                .filter(content -> content.isApproved() && (allowedUsers.isEmpty() || allowedUsers.contains(content.getAuthorId())))
                .collect(Collectors.toList());
    }

    public void setContestWinners(String contestId, List<String> winners) {
        get(Objects.requireNonNull(contestId, "Id is not valid")).setWinners(Objects.requireNonNull(winners, "Winners list is empty"));
    }
}
