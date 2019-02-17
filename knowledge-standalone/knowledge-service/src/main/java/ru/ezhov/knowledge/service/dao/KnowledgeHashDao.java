package ru.ezhov.knowledge.service.dao;

import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.view.KnowledgeHash;

import java.util.List;
import java.util.Map;

public interface KnowledgeHashDao {
    Map<String, KnowledgeHash> getKnowledgeHashMap();

    List<KnowledgeHash> getKnowledgeHashes();

    void loadKnowledge(PropertiesHolder propertiesHolder) throws Exception;
}
