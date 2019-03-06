package ru.ezhov.knowledge.service.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.service.controller.KnowledgeAllRoute;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class KnowledgeClient {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeClient.class);
    private String type;
    private String name;
    private String rawUrl;
    private String description;
    private String url;
    private boolean isPublic;

    private KnowledgeClient() {
    }

    private KnowledgeClient(String type, String name, String rawUrl, String description, String url, boolean isPublic) {
        this.type = type;
        this.name = name;
        this.rawUrl = rawUrl;
        this.description = description;
        this.url = url;
        this.isPublic = isPublic;
    }

    public static KnowledgeClient from(String server, Knowledge knowledge, NameParser parser) throws Exception {
        String rawUrl = URLEncoder
                .encode(
                        knowledge.getRawUrl(),
                        StandardCharsets.UTF_8.name()
                );
        logger.debug("сгенерированная сырая ссылка: {}", rawUrl);
        return new KnowledgeClient(
                parser.name(knowledge.getName()),
                knowledge.getName(),
                server + "/knowledge?raw=" + rawUrl,
                knowledge.getDescription(),
                knowledge.getUrl(),
                knowledge.isPublic()
        );
    }

    public String getType() {
        return type;
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
