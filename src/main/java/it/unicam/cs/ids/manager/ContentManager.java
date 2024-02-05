package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.content.Content;
import it.unicam.cs.ids.model.content.ContentReport;
import it.unicam.cs.ids.repository.ContentReportRepository;
import it.unicam.cs.ids.repository.ContentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public abstract class ContentManager<C extends Content, R extends ContentRepository<C>> {

    protected final R repository;
    private final ContentReportRepository reportRepository;

    public ContentManager(R repository, ContentReportRepository reportRepository) {
        this.repository = repository;
        this.reportRepository = reportRepository;
    }

    public C get(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<C> get(List<String> ids) {
        return repository.findAllById(ids);
    }

    public List<C> getAll() {
        return repository.findAll();
    }

    public List<C> getInDateRange(Date start, Date end) {
        return repository.findAllByCreationDateBetween(start, end);
    }

    public List<C> getContentToApprove() {
        return repository.findAllByApproved(false);
    }

    public List<C> getApproved(List<String> ids) {
        return repository.findAllByApprovedTrueAndIdIn(ids);
    }

    public String submit(C content) {
        return repository.save(content).getId();
    }

    public void submit(List<C> content) {
        repository.saveAll(content);
    }

    public List<C> find(String keywords) {
        return repository.findAllByText(keywords);
    }

    public void approve(String id) {
        C contentToApprove = get(id);
        Objects.requireNonNull(contentToApprove, "Content not found");
        contentToApprove.setApproved(true);
        repository.save(contentToApprove);
    }

    public void remove(String id) {
        repository.deleteById(id);
    }

    public String report(String contentId, String reporterId, String message) {
        C content = get(contentId);
        if (content == null) {
            throw new IllegalArgumentException("Content with id " + contentId + " not found");
        }

        ContentReport report = new ContentReport(contentId, reporterId, content.getType(), message);
        return reportRepository.save(report).getId();
    }

    public List<C> getAllowedUsersApprovedContentsInDateRange(LocalDateTime start, LocalDateTime end, List<String> allowedUsers) {
        if(allowedUsers.isEmpty())
            return repository.findAllApprovedContentsInDateRange(start, end);
        return repository.findAllAllowedUsersApprovedContentsInDateRange(start, end, allowedUsers);
    }
}
