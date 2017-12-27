package ru.ezhov.knowledge.service.view;

public class KnowledgeClient {
    private String name;
    private String rawUrl;
    private String description;
    private String url;
    private boolean isPublic;

    public KnowledgeClient() {
    }

    public KnowledgeClient(String name, String rawUrl, String description, String url, boolean isPublic) {
        this.name = name;
        this.rawUrl = rawUrl;
        this.description = description;
        this.url = url;
        this.isPublic = isPublic;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawUrl() {
        return this.rawUrl;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
    }

    public String toString() {
        return "KnowledgeClient{name='" + this.name + '\'' + ", rawUrl='" + this.rawUrl + '\'' + ", description='" + this.description + '\'' + ", url='" + this.url + '\'' + ", isPublic=" + this.isPublic + '}';
    }
}
