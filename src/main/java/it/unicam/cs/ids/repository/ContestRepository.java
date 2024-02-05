package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.contest.GenericContest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ContestRepository extends MongoRepository<GenericContest, String>{

    @Query("{ 'createdBy': ?0, 'endDate': { $lt: new LocalDateTime() } }")
    List<GenericContest> findCompletedContestsByAnimatorId(String animatorId);

    @Query("{ 'createdBy': ?0, 'endDate': { $gt: new LocalDateTime() } }")
    List<GenericContest> findPendingContestsByAnimatorId(String animatorId);

}
