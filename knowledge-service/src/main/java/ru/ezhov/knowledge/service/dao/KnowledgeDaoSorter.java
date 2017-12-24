package ru.ezhov.knowledge.service.dao;

import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.common.KnowledgeDao;
import ru.ezhov.knowledge.common.PropertiesHolder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KnowledgeDaoSorter implements KnowledgeDao {
    private KnowledgeDao knowledgeDao;
    private Comparator<Knowledge> comparator;

    public KnowledgeDaoSorter(KnowledgeDao knowledgeDao, Comparator<Knowledge> comparator) {
        this.knowledgeDao = knowledgeDao;
        this.comparator = comparator;
    }

    @Override
    public List<Knowledge> getKnowledges(PropertiesHolder propertiesHolder) throws Exception {
        List<Knowledge> knowledgeTransforms = knowledgeDao.getKnowledges(propertiesHolder);
        Collections.sort(knowledgeTransforms, comparator);
        return knowledgeTransforms;
    }
}
