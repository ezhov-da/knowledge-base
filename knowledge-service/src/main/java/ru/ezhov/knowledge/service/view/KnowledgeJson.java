package ru.ezhov.knowledge.service.view;

import org.codehaus.jackson.map.ObjectMapper;
import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.dao.KnowledgeHashDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KnowledgeJson {
    private KnowledgeHashDao knowledgeHashDao;

    public KnowledgeJson(KnowledgeHashDao knowledgeHashDao) {
        this.knowledgeHashDao = knowledgeHashDao;
    }

    public String getJson(PropertiesHolder propertiesHolder, Date dateLastUpdate) throws Exception {
        knowledgeHashDao.loadKnowledge(propertiesHolder);

        List<KnowledgeHash> knowledgeTransformList = knowledgeHashDao.getKnowledgeHashes();
        List<KnowledgeClient> knowledgeClientList = new ArrayList<>();
        knowledgeTransformList.forEach(k -> knowledgeClientList.add(createClientKnowledge(propertiesHolder, k)));

        KnowledgesAllJsonAnswer knowledgesAllJsonAnswer =
                new KnowledgesAllJsonAnswer(dateLastUpdate, knowledgeClientList);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(knowledgesAllJsonAnswer);
    }

    private KnowledgeClient createClientKnowledge(PropertiesHolder propertiesHolder, KnowledgeHash knowledgeHash) {
        KnowledgeClient knowledgeClient;
        Knowledge k = knowledgeHash.getKnowledge();
        String knowledgeHashString = knowledgeHash.getHash();
        knowledgeClient = new KnowledgeClient(
                k.getName(),
                String.format(propertiesHolder.getURL() + "/knowledge/%s/raw", knowledgeHashString),
                k.getDescription(),
                String.format(propertiesHolder.getURL() + "/knowledge/%s/url", knowledgeHashString),
                k.isPublic()
        );
        return knowledgeClient;
    }
}
