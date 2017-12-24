package ru.ezhov.knowledge.service.controller;

import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.common.KnowledgeDao;
import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.dao.KnowledgeDaoCacheSingleton;
import ru.ezhov.knowledge.service.view.KnowledgeHash;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class KnowledgeUrlRoute implements Route {
    private static String HEAD_PARAM1 = "Access-Control-Allow-Origin";
    private static String HEAD_VALUE1 = "*";
    private static String HEAD_PARAM2 = "charset";
    private static String HEAD_VALUE2 = "utf-8";
    private static String TYPE = "application/json";
    private PropertiesHolder propertiesHolder;

    public KnowledgeUrlRoute(PropertiesHolder propertiesHolder) {
        this.propertiesHolder = propertiesHolder;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String hash = request.params(":hash");

        KnowledgeDaoCacheSingleton knowledgeDaoCacheSingleton = KnowledgeDaoCacheSingleton.getInstance();
        Map<String, KnowledgeHash> knowledgeHashMap = knowledgeDaoCacheSingleton.getKnowledgeHashMap();
        KnowledgeHash knowledgeHash = knowledgeHashMap.get(hash);
        if (knowledgeHash == null) {
            return "Opps, knowledge not found";
        }

        Knowledge knowledge = knowledgeHash.getKnowledge();
        if (knowledge.isPublic()) {
            response.redirect(knowledge.getUrl());
        } else {
            String password = request.queryParams("password");
            String passFromConfig = propertiesHolder.getPassword();
            if (passFromConfig.equals(password)) {
                response.redirect(knowledge.getUrl());
            } else {
                response.header(HEAD_PARAM1, HEAD_VALUE1);
                response.header(HEAD_PARAM2, HEAD_VALUE2);
                response.type(TYPE);
                response.body("{'error':'wrong password for secure link'}");
            }
        }
        return hash;
    }
}
