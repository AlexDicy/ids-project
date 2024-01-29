package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.content.Content;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;
import java.util.List;

@NoRepositoryBean
public interface ContentRepository<T extends Content> extends MongoRepository<T, String> {

    List<T> findAllBy(TextQuery textQuery);

    List<T> findAllByApproved(boolean approved);

    List<T> findAllByCreationDateBetween(Date start, Date end);
}
