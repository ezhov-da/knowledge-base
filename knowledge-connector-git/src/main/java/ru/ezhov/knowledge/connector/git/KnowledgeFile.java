package ru.ezhov.knowledge.connector.git;

class KnowledgeFile {
    private String name;
    private String rawUrl;

    public KnowledgeFile(String name, String rawUrl) {
        this.name = name;
        this.rawUrl = rawUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawUrl() {
        return rawUrl;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    @Override
    public String toString() {
        return "KnowledgeFile{" +
                "name='" + name + '\'' +
                ", rawUrl='" + rawUrl + '\'' +
                '}';
    }
}
