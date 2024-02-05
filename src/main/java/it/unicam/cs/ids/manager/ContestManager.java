package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.controller.dto.ContestDTO;
import it.unicam.cs.ids.model.contest.*;
import it.unicam.cs.ids.repository.ContestRepository;
import it.unicam.cs.ids.util.BadRequestException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ContestManager {

    protected final ContestRepository contestRepository;
    protected final List<Contest> contestList = new ArrayList<>();

    public ContestManager(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    public GenericContest get(String id) {
        Objects.requireNonNull(id, "Id is not valid");
        return contestRepository.findById(id).orElse(null);
    }

    public String submit(ContestDTO dto) {
        switch (dto.type()) {
            case CONTENT_CONTEST -> {
                return contestRepository.save(new ContentContest(dto.name(), dto.description(), dto.startDate(), dto.endDate(), dto.createdBy(), dto.allowedUsers())).getId();
            }
            case POI_CONTEST -> {
                if (dto.idPOI() == null) {
                    throw new BadRequestException("POI id is required");
                }
                return contestRepository.save(new POIContest(dto.name(), dto.description(), dto.startDate(), dto.endDate(), dto.createdBy(), dto.allowedUsers(), dto.idPOI())).getId();
            }
            default -> throw new IllegalArgumentException("Contest type not supported");
        }
    }

    public List<GenericContest> getAnimatorCompletedContests(String animatorId) {
        Objects.requireNonNull(animatorId, "Id is not valid");
        return contestRepository.findCompletedContestsByAnimatorId(animatorId);
    }

    public List<GenericContest> getAnimatorPendingContests(String animatorId) {
        Objects.requireNonNull(animatorId, "Id is not valid");
        return contestRepository.findPendingContestsByAnimatorId(animatorId);
    }

    public String setContestWinners(String contestId, List<String> winners) {
        Objects.requireNonNull(contestId, "Id is not valid");
        Objects.requireNonNull(winners, "Winners list is not valid");
        GenericContest contest =  contestRepository.findById(contestId).orElseThrow(() -> new InvalidParameterException("Invalid contest id"));
        contest.setWinners(winners);
        contestRepository.save(contest);
        return "Winners set for contest " + contestId;
    }

}
