package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.content.ContentReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentReportRepository extends MongoRepository<ContentReport, String> {
}
