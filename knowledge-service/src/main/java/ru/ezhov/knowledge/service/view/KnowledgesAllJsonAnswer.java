package ru.ezhov.knowledge.service.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class KnowledgesAllJsonAnswer {
    private String lastUpdate;
    private List<KnowledgeContextData> context;
    private List<KnowledgeClient> knowledges;
    private DateFormat dateFormat = new SimpleDateFormat("E dd.MM.yyyy HH:mm:ss");

    public KnowledgesAllJsonAnswer() {
    }

    public KnowledgesAllJsonAnswer(Date lastUpdate, List<KnowledgeClient> knowledges) {
        this.lastUpdate = convertDate(lastUpdate);
        this.context = KnowledgeContext.from(knowledges).getContext();
        this.knowledges = knowledges;
    }

    private String convertDate(Date date) {
        return dateFormat.format(date);
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdateDate(Date lastUpdate) {
        this.lastUpdate = convertDate(lastUpdate);
    }

    public List<KnowledgeClient> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<KnowledgeClient> knowledges) {
        this.knowledges = knowledges;
    }

    public List<KnowledgeContextData> getContext() {
        return context;
    }
}
