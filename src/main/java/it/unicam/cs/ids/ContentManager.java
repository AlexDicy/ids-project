package it.unicam.cs.ids;

import it.unicam.cs.ids.model.Content;

import java.util.List;

public interface ContentManager<C extends Content> {

    C get(String id);

    void submit(C content);

    List<C> getAll();

    List<C> find(String keywords);

    void approve(String id);

    void remove(String id);

    List<C> getContentToApprove();
}