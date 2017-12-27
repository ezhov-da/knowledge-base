package ru.ezhov.knowledge.service.controller;

import org.codehaus.jackson.map.ObjectMapper;
import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.dao.KnowledgeDaoCacheSingleton;
import ru.ezhov.knowledge.service.view.KnowledgeHash;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class KnowledgeLinksRoute implements Route {
    private static String HEAD_PARAM1 = "Access-Control-Allow-Origin";
    private static String HEAD_VALUE1 = "*";
    private static String HEAD_PARAM2 = "charset";
    private static String HEAD_VALUE2 = "utf-8";
    private static String TYPE = "application/json";
    private PropertiesHolder propertiesHolder;

    public KnowledgeLinksRoute(PropertiesHolder propertiesHolder) {
        this.propertiesHolder = propertiesHolder;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.header(HEAD_PARAM1, HEAD_VALUE1);
        response.header(HEAD_PARAM2, HEAD_VALUE2);
        response.type(TYPE);
        String hash = getHash(request);
        String data = getData(request);

        KnowledgeHash knowledgeHash = getKnowledgeHash(hash);

        Map<String, String> mapResponse = new HashMap<>();

        if (knowledgeHash == null) {
            mapResponse.put("error", "Oops, not found hash...");
        } else {

            try {
                Knowledge knowledge = knowledgeHash.getKnowledge();
                DataType dataType = DataType.valueOf(data.toUpperCase());
                switch (dataType) {
                    case URL:
                        String redirect = processing(
                                knowledge,
                                request,
                                new KnowledgeResponsePreparingUrl()
                        );
                        mapResponse.put("redirect", redirect);
                        break;
                    case RAW:
                        String rawData = processing(
                                knowledge,
                                request,
                                new KnowledgeResponsePreparingRaw()
                        );
                        mapResponse.put("text", rawData);
                        break;
                    default:
                        throw new Exception("Data type is error...");
                }
            } catch (Exception ex) {
                mapResponse.put("error", ex.getMessage());
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(mapResponse);
    }

    private String getHash(Request request) {
        return request.params(":hash");
    }

    private String getData(Request request) {
        return request.params(":data");
    }

    private KnowledgeHash getKnowledgeHash(String hash) throws Exception {
        KnowledgeDaoCacheSingleton knowledgeDaoCacheSingleton = KnowledgeDaoCacheSingleton.getInstance();
        if (!knowledgeDaoCacheSingleton.isCached()) {
            knowledgeDaoCacheSingleton.loadKnowledge(propertiesHolder);
        }

        Map<String, KnowledgeHash> knowledgeHashMap = knowledgeDaoCacheSingleton.getKnowledgeHashMap();
        return knowledgeHashMap.get(hash);
    }

    private String processing(
            Knowledge knowledge,
            Request request,
            KnowledgeResponsePreparing knowledgeResponsePreparing) throws Exception {
        String result;
        if (knowledge.isPublic()) {
            result = knowledgeResponsePreparing.prepare(knowledge);
        } else {
            String password = request.queryParams("password");
            if (password == null || "".equals(password)) {
                throw new Exception("Try get private data, password must be set...");
            }
            String passFromConfig = propertiesHolder.getPassword();
            if (passFromConfig.equals(password)) {
                result = knowledgeResponsePreparing.prepare(knowledge);
            } else {
                throw new Exception("Wrong password for secure link...");
            }
        }
        return result;
    }
}
