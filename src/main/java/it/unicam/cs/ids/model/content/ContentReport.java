package it.unicam.cs.ids.model.content;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "reports")
public class ContentReport {
    @Id
    private String id;
    private final String contentId;
    private final String reporterId;
    private final ContentType type;
    private final String reason;

    public ContentReport(String contentId, String reporterId, ContentType type, String reason) {
        this.contentId = contentId;
        this.reporterId = reporterId;
        this.type = type;
        this.reason = reason;
    }
}
