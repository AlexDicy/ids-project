package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.controller.dto.ContestDTO;
import it.unicam.cs.ids.manager.*;
import it.unicam.cs.ids.model.content.Content;
import it.unicam.cs.ids.model.contest.GenericContest;
import it.unicam.cs.ids.model.contest.POIContest;
import it.unicam.cs.ids.util.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/contest")
public class ContestController<C extends GenericContest> {
    private final ContestManager contestManager;
    private final ItineraryManager itineraryManager;
    private final POIManager poiManager;
    private final ImageManager imageManager;

    public ContestController(ContestManager contestManager, POIManager poiManager, ImageManager imageManager, ItineraryManager itineraryManager) {
        this.poiManager = poiManager;
        this.imageManager = imageManager;
        this.itineraryManager = itineraryManager;
        this.contestManager = contestManager;
    }

    @GetMapping("")
    public GenericContest getById(@RequestParam String id) {

        GenericContest contest = contestManager.get(id);
        if (contest == null) {
            throw new BadRequestException("Contest not found");
        }
        System.out.println("returning contest");
        return contest;
    }

    @PostMapping("/submit")
    public String submit(@RequestBody @Valid ContestDTO dto) {
        return contestManager.submit(dto);
    }

    @GetMapping("/completed")
    public List<GenericContest> getCompletedContestsByAnimatorId(@RequestParam String animatorId) {
        return contestManager.getAnimatorCompletedContests(animatorId);
    }

    @GetMapping("/pending")
    public List<GenericContest> getPendingContestsByAnimatorId(@RequestParam String animatorId) {
        return contestManager.getAnimatorPendingContests(animatorId);
    }

    @PutMapping("/winners")
    public String setWinners(@RequestParam String contestId, @RequestBody List<String> winners) {
        return contestManager.setContestWinners(contestId, winners);
    }

    @GetMapping("/contents")
    public List<Content> getContestContents(@RequestParam String contestId) {
        GenericContest contest = contestManager.get(contestId);
        if (contest == null) {
            throw new NoSuchElementException("Contest not found");
        }

        List<Content> contestValidContents = new ArrayList<>();
        switch (contest.getType()) {
            case CONTENT_CONTEST -> {
                contestValidContents.addAll(poiManager.getAllowedUsersApprovedContentsInDateRange(contest.getStartDate(), contest.getEndDate(), contest.getAllowedUsers()));
                contestValidContents.addAll(itineraryManager.getAllowedUsersApprovedContentsInDateRange(contest.getStartDate(), contest.getEndDate(), contest.getAllowedUsers()));
            }
            case POI_CONTEST -> {
                POIContest poiContest = (POIContest) contest;
                String poiId = poiContest.getIdPOI();
                contestValidContents.addAll(imageManager.getAllowedUsersApprovedPoiImagesInDateRange(poiId, contest.getStartDate(), contest.getEndDate(), contest.getAllowedUsers()));
            }
        }

        return contestValidContents;
    }

}
