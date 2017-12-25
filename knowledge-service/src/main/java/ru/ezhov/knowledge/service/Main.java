package ru.ezhov.knowledge.service;

import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.controller.KnowledgeAllRoute;
import ru.ezhov.knowledge.service.controller.KnowledgeLinksRoute;

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
        post("/knowledge/:hash/:data", new KnowledgeLinksRoute(propertiesHolder));

    }
}
