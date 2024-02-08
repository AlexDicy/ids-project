package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.controller.dto.ContestDTO;
import it.unicam.cs.ids.model.contest.Contest;
import it.unicam.cs.ids.model.contest.ContestFactory;
import it.unicam.cs.ids.repository.ContestRepository;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

@Service
public class ContestManager {

    private final ContestRepository contestRepository;
    private final ContestFactory contestFactory;

    public ContestManager(ContestRepository contestRepository, ContestFactory contestFactory) {
        this.contestRepository = contestRepository;
        this.contestFactory = contestFactory;
    }

    public Contest get(String id) {
        Objects.requireNonNull(id, "Id is not valid");
        return contestRepository.findById(id).orElse(null);
    }

    public String submit(ContestDTO dto) {
        Contest contest = contestFactory.createContest(dto);
        return contestRepository.save(contest).getId();
    }

    public List<Contest> getAnimatorCompletedContests(String animatorId) {
        Objects.requireNonNull(animatorId, "Id is not valid");
        return contestRepository.findCompletedContestsByAnimatorId(animatorId);
    }

    public List<Contest> getAnimatorPendingContests(String animatorId) {
        Objects.requireNonNull(animatorId, "Id is not valid");
        return contestRepository.findPendingContestsByAnimatorId(animatorId);
    }

    public String setContestWinners(String contestId, List<String> winners) {
        Objects.requireNonNull(contestId, "Id is not valid");
        Objects.requireNonNull(winners, "Winners list is not valid");
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new InvalidParameterException("Invalid contest id"));
        contest.setWinners(winners);
        contestRepository.save(contest);
        return "Winners set for contest " + contestId;
    }

}
