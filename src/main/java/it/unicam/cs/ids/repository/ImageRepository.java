package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.content.Image;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ImageRepository extends ContentRepository<Image> {

    List<Image> findAllByIdPoi(String idPOI);

    List<Image> findAllByIdPoiAndCreationDateBetween(String idPOI, LocalDateTime start, LocalDateTime end);

    @Query("{'approved' : true, " +
            "'idPoi' : ?0, " +
            "'creationDate' : { $gte: ?1 }, 'endDate' : { $lte: ?2 } ," +
            "'createdBy' : { $in: ?3 } }")
    List<Image> findAllAllowedUsersApprovedPoiImagesInDateRange(String idPOI, LocalDateTime start, LocalDateTime end, List<String> allowedUsers);

    @Query("{'approved' : true, " +
            "'idPoi' : ?0, " +
            "'creationDate' : { $gte: ?1 }, 'endDate' : { $lte: ?2 } }")
    List<Image> findAllApprovedPoiImagesInDateRange(String idPOI, LocalDateTime start, LocalDateTime end);
}
