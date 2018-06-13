package ru.ezhov.knowledge.service.controller;

import org.codehaus.jackson.map.ObjectMapper;
import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.common.KnowledgeDao;
import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.dao.KnowledgeDaoSorter;
import ru.ezhov.knowledge.service.dao.KnowledgeDaoSource;
import ru.ezhov.knowledge.service.view.KnowledgeClient;
import ru.ezhov.knowledge.service.view.KnowledgeNameComparator;
import ru.ezhov.knowledge.service.view.KnowledgesAllJsonAnswer;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        //TODO: пока убираем хеширование и кэширование
        //KnowledgeJson knowledgeJson = new KnowledgeJson(KnowledgeDaoCacheSingleton.getInstance());
        KnowledgeDao knowledgeDao = new KnowledgeDaoSorter(
                new KnowledgeDaoSource(),
                new KnowledgeNameComparator()
        );

        List<Knowledge> knowledges = knowledgeDao.getKnowledges(propertiesHolder);
        List<KnowledgeClient> knowledgeClientList = new ArrayList<>();
        knowledges.forEach(k -> knowledgeClientList.add(
                new KnowledgeClient(
                        k.getName(),
                        k.getRawUrl(),
                        k.getDescription(),
                        k.getUrl(),
                        k.isPublic()
                )
        ));

        KnowledgesAllJsonAnswer knowledgesAllJsonAnswer =
                new KnowledgesAllJsonAnswer(dateLastUpdate, knowledgeClientList);

        dateLastUpdate = lastUpdate;

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(knowledgesAllJsonAnswer);
    }

}
