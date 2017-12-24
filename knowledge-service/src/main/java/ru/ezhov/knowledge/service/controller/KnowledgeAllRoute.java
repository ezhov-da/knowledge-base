package ru.ezhov.knowledge.service.controller;

import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.dao.KnowledgeDaoCacheSingleton;
import ru.ezhov.knowledge.service.view.KnowledgeJson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Date;

public class KnowledgeAllRoute implements Route {
    private static String HEAD_PARAM = "Access-Control-Allow-Origin";
    private static String HEAD_VALUE = "*";
    private static String TYPE = "application/json";

    private static Date dateLastUpdate = new Date();
    private static String json;

    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.header(HEAD_PARAM, HEAD_VALUE);
        response.type(TYPE);
        json = getJson();
        return json;
    }

    private String getJson() throws Exception {
        String newJson;
        try {
            Date dateNow = new Date();
            PropertiesHolder propertiesHolder = new PropertiesHolder();
            long timeout = propertiesHolder.getTimeoutUpdate();
            synchronized (dateLastUpdate) {
                if (ipPassedTime(dateNow, dateLastUpdate, timeout)
                        || json == null) {
                    newJson = createJson(propertiesHolder, dateNow);
                } else {
                    newJson = json;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return newJson;
    }

    private boolean ipPassedTime(Date now, Date last, long timeout) {
        return now.getTime() - last.getTime() >= timeout;
    }

    private String createJson(PropertiesHolder propertiesHolder, Date lastUpdate) throws Exception {
        KnowledgeJson knowledgeJson = new KnowledgeJson(KnowledgeDaoCacheSingleton.getInstance());
        dateLastUpdate = lastUpdate;
        return knowledgeJson.getJson(propertiesHolder, dateLastUpdate);
    }

}
