package ru.ezhov.knowledge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.controller.KnowledgeAllRoute;
import ru.ezhov.knowledge.service.controller.KnowledgeRawRoute;
import ru.ezhov.knowledge.service.controller.KnowledgeLinksRoute;

import static spark.Spark.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeAllRoute.class);

    public static void main(String[] args) {
        logger.info("запуск приложения...");
        try {
            initProperties();
            port(PropertiesHolder.getPort());
            logger.info("порт приложения {}", PropertiesHolder.getPort());
            if (PropertiesHolder.isHttpsEnable()) {
                secure(
                        PropertiesHolder.getHttpsKeyStorePath(),
                        PropertiesHolder.getHttpKeyStorePassword(),
                        null,
                        null
                );
            }
            options("/knowledges", new KnowledgeAllRoute());
            get("/knowledges", new KnowledgeAllRoute());
            options("/knowledge", new KnowledgeRawRoute());
            get("/knowledge", new KnowledgeRawRoute());
            post("/knowledge/:hash/:data", new KnowledgeLinksRoute());
            logger.info("приложение запущено");
        } catch (Exception e) {
            logger.error("Ошибка запуска приложения", e);
        }
    }

    private static void initProperties() throws Exception {
        PropertiesHolder.initPropertiesHolder();
    }
}
