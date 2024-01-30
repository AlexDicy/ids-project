package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.content.Content;
import it.unicam.cs.ids.repository.ContentRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class ContentManager<C extends Content, R extends ContentRepository<C>> {

    protected final R repository;

    public ContentManager(R repository) {
        this.repository = repository;
    }

    public C get(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<C> getApproved(List<String> ids) {
        return repository.findAllByApprovedTrueAndIdIn(ids);
    }

    public void submit(C content) {
        repository.save(content);
    }

    public void submit(List<C> content) {
        repository.saveAll(content);
    }

    public List<C> getAll() {
        return repository.findAll();
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

    public List<C> getContentToApprove() {
        return repository.findAllByApproved(false);
    }

    public List<C> getInDateRange(Date start, Date end) {
        return repository.findAllByCreationDateBetween(start, end);
    }
}
