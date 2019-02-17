package ru.ezhov.knowledge.connector.git;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class KnowledgeGit {
    private String description;
    private String url;
    private List<KnowledgeFile> knowledgeFiles = new ArrayList<>();
    private boolean isPublic;
    private Date date;

    public KnowledgeGit(String description, String url, boolean isPublic, Date date) {
        this.description = description;
        this.url = url;
        this.isPublic = isPublic;
        this.date = date;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<KnowledgeFile> getKnowledgeFiles() {
        return knowledgeFiles;
    }

    public void setKnowledgeFiles(List<KnowledgeFile> knowledgeFiles) {
        if (knowledgeFiles == null)
            throw new NullPointerException("List must be not null");
        this.knowledgeFiles = knowledgeFiles;
    }

    @Override
    public String toString() {
        return "KnowledgeGit{" +
                "description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", knowledgeFiles=" + knowledgeFiles +
                ", isPublic=" + isPublic +
                ", date=" + date +
                '}';
    }
}
