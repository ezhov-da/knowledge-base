package ru.ezhov.knowledge.service;

import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.controller.KnowledgeAllRoute;
import ru.ezhov.knowledge.service.controller.KnowledgeRawTextRoute;
import ru.ezhov.knowledge.service.controller.KnowledgeUrlRoute;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        port(propertiesHolder.getPort());

        if (propertiesHolder.isHttpsEnable()) {
            secure(
                    propertiesHolder.getHttpsKeyStorePath(),
                    propertiesHolder.getHttpKeyStorePassword(),
                    null,
                    null
            );
        }

        get("/knowledges", new KnowledgeAllRoute());
        post("/knowledge/:hash/raw", new KnowledgeRawTextRoute());
        post("/knowledge/:hash/url", new KnowledgeUrlRoute());

    }
}
