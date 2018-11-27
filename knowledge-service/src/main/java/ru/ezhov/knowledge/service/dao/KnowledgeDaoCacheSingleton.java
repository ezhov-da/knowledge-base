package ru.ezhov.knowledge.service.dao;

import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.common.KnowledgeDao;
import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.view.KnowledgeHash;
import ru.ezhov.knowledge.service.view.KnowledgeNameComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: change class, because bad... very bad
//TODO: add cache functions...
public class KnowledgeDaoCacheSingleton implements KnowledgeHashDao {
    private static KnowledgeDaoCacheSingleton gitDaoSingleton;
    private List<KnowledgeHash> cacheLastKnowledgeTransforms = new ArrayList<>();
    private Map<String, KnowledgeHash> hashMap = new HashMap();

    private KnowledgeDao knowledgeDao;

    private KnowledgeDaoCacheSingleton() {
        knowledgeDao = new KnowledgeDaoSorter(
                new KnowledgeDaoSource(),
                new KnowledgeNameComparator()
        );
    }

    public final static synchronized KnowledgeDaoCacheSingleton getInstance() {
        if (gitDaoSingleton == null) {
            gitDaoSingleton = new KnowledgeDaoCacheSingleton();
        }
        return gitDaoSingleton;
    }

    public final synchronized boolean isCached() {
        gitDaoSingleton = getInstance();
        return !cacheLastKnowledgeTransforms.isEmpty();
    }

    public final synchronized List<KnowledgeHash> getKnowledgeHashes() {
        gitDaoSingleton = getInstance();
        if (isCached()) {
            return cacheLastKnowledgeTransforms;
        } else {
            throw new IllegalStateException("Use [loadKnowledge] method for check cache...");
        }
    }

    public synchronized void loadKnowledge(PropertiesHolder propertiesHolder) throws Exception {
        List<Knowledge> knowledges = knowledgeDao.getKnowledges();
        cacheLastKnowledgeTransforms.removeAll(cacheLastKnowledgeTransforms);
        knowledges.forEach(k -> cacheLastKnowledgeTransforms.add(new KnowledgeHash(k)));
        cacheLastKnowledgeTransforms.forEach(kh -> hashMap.put(kh.getHash(), kh));
    }

    @Override
    public Map<String, KnowledgeHash> getKnowledgeHashMap() {
        gitDaoSingleton = getInstance();
        if (isCached()) {
            return hashMap;
        } else {
            throw new IllegalStateException("Use [loadKnowledge] method for check cache...");
        }
    }
}
