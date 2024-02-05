package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.content.Content;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDateTime;
import java.util.List;

@NoRepositoryBean
public interface ContentRepository<T extends Content> extends MongoRepository<T, String> {

    @Aggregation({
            "{$search: {text: {query: ?0, path: ['name', 'description'], fuzzy: {maxEdits: 2}}}}",
            "{$match: {approved: true}}",
            "{$addFields: {searchScore:  {$meta: 'searchScore'}}}",
    })
    List<T> findAllByText(String query);

    List<T> findAllByApproved(boolean approved);

    List<T> findAllByCreationDateBetween(LocalDateTime start, LocalDateTime end);

    List<T> findAllByApprovedTrueAndIdIn(List<String> ids);

    @Query("{'approved' : true, 'creationDate' : { $gte: ?0, $lte: ?1 }, 'createdBy' : { $in: ?2 }}")
    List<T> findAllAllowedUsersApprovedContentsInDateRange(LocalDateTime start, LocalDateTime end, List<String> allowedUsers);

    @Query("{'approved' : true, 'creationDate' : { $gte: ?0, $lte: ?1 }}")
    List<T> findAllApprovedContentsInDateRange(LocalDateTime start, LocalDateTime end);
}

