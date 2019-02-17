package ru.ezhov.knowledge.service.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.common.KnowledgeDao;
import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.dao.KnowledgeDaoSorter;
import ru.ezhov.knowledge.service.dao.KnowledgeDaoSource;
import ru.ezhov.knowledge.service.view.DashNameParser;
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
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeAllRoute.class);

    private static String HEAD_PARAM = "Access-Control-Allow-Origin";
    private static String HEAD_VALUE = "*";
    private static String TYPE_APPLICATION_JSON = "application/json";
    private static String TYPE_PLANE_TEXT = "text/plane";

    private static Date dateLastUpdate = new Date();
    private static Object lockObject = new Object();
    private static String json;

    @Override
    public Object handle(Request request, Response response) throws Exception {
        logger.debug("получение знаний методом: {}", request.requestMethod());
        if ("OPTIONS".equals(request.requestMethod())) {
            response.header("Access-Control-Allow-Headers", "x-requested-with");
            response.header("Access-Control-Allow-Method", "GET");
            response.header(HEAD_PARAM, HEAD_VALUE);
            response.type(TYPE_PLANE_TEXT);
            return "OK";
        } else {
            json = getJson();
            response.header(HEAD_PARAM, HEAD_VALUE);
            response.type(TYPE_APPLICATION_JSON);
            return json;
        }
    }

    private String getJson() throws Exception {
        String newJson;
        try {
            Date dateNow = new Date();
            long timeout = PropertiesHolder.getTimeoutUpdate();
            synchronized (lockObject) {
                if (ipPassedTime(dateNow, dateLastUpdate, timeout) || json == null) {
                    newJson = createJson(dateNow);
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

    private String createJson(Date lastUpdate) throws Exception {
        long start = System.currentTimeMillis();
        //TODO: пока убираем хеширование и кэширование
        //KnowledgeJson knowledgeJson = new KnowledgeJson(KnowledgeDaoCacheSingleton.getInstance());
        KnowledgeDao knowledgeDao = new KnowledgeDaoSorter(
                new KnowledgeDaoSource(),
                new KnowledgeNameComparator()
        );
        List<Knowledge> knowledges = knowledgeDao.getKnowledges();
        List<KnowledgeClient> knowledgeClientList = new ArrayList<>();
        knowledges.forEach(k -> knowledgeClientList.add(
                KnowledgeClient.from(k, new DashNameParser())
        ));
        KnowledgesAllJsonAnswer knowledgesAllJsonAnswer =
                new KnowledgesAllJsonAnswer(dateLastUpdate, knowledgeClientList);
        dateLastUpdate = lastUpdate;
        ObjectMapper objectMapper = new ObjectMapper();
        logger.debug("Получен список знаний за {} ms", System.currentTimeMillis() - start);
        return objectMapper.writeValueAsString(knowledgesAllJsonAnswer);
    }

}
