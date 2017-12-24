package ru.ezhov.knowledge.service.view;

import java.util.Date;
import java.util.List;

public class KnowledgesAllJsonAnswer {
    private String lastUpdate;
    private List<KnowledgeClient> knowledges;

    public KnowledgesAllJsonAnswer(Date lastUpdate, List<KnowledgeClient> knowledges) {
        this.lastUpdate = lastUpdate.toString();
        this.knowledges = knowledges;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate.toString();
    }

    public List<KnowledgeClient> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<KnowledgeClient> knowledges) {
        this.knowledges = knowledges;
    }
}
