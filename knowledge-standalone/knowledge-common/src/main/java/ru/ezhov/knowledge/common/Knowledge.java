package ru.ezhov.knowledge.common;

import java.util.Date;

public class Knowledge{
    private String name;
    private String rawUrl;
    private String description;
    private String url;
    private boolean isPublic;
    private Date date;

    public Knowledge(String name, String rawUrl, String description, String url, boolean isPublic, Date date) {
        this.name = name;
        this.rawUrl = rawUrl;
        this.description = description;
        this.url = url;
        this.isPublic = isPublic;
        this.date = date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Knowledge{" +
                "name='" + name + '\'' +
                ", rawUrl='" + rawUrl + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", isPublic=" + isPublic +
                ", date=" + date +
                '}';
    }

}
