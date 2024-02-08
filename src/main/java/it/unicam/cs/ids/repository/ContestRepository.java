package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.contest.Contest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ContestRepository extends MongoRepository<Contest, String>{

    @Query("{ 'createdBy': ?0, 'endDate': { $lt: new Date() } }")
    List<Contest> findCompletedContestsByAnimatorId(String animatorId);

    @Query("{ 'createdBy': ?0, 'endDate': { $gt: new Date() } }")
    List<Contest> findPendingContestsByAnimatorId(String animatorId);

}
