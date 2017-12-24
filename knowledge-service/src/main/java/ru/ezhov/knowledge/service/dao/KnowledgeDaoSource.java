package ru.ezhov.knowledge.service.dao;

import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.common.KnowledgeDao;
import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.connector.git.KnowledgeGitDao;

import java.util.List;

public class KnowledgeDaoSource implements KnowledgeDao {

    public List<Knowledge> getKnowledges(PropertiesHolder propertiesHolder) throws Exception {
        KnowledgeDao knowledgeDao = new KnowledgeGitDao(
                propertiesHolder.getToken(),
                propertiesHolder.getUser()
        );
        List<Knowledge> knowledgeList = knowledgeDao.getKnowledges(null);
        return knowledgeList;
    }
}
