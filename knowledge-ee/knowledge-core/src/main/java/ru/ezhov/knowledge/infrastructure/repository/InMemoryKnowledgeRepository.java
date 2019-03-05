package ru.ezhov.knowledge.infrastructure.repository;

import ru.ezhov.knowledge.core.repository.KnowledgeEntity;
import ru.ezhov.knowledge.core.repository.KnowledgeRepository;
import ru.ezhov.knowledge.core.repository.KnowledgeRepositoryException;

import java.util.List;

public class InMemoryKnowledgeRepository implements KnowledgeRepository {
    @Override
    public List<KnowledgeEntity> all() throws KnowledgeRepositoryException {
        return null;
    }

    @Override
    public KnowledgeEntity getById(String guid) throws KnowledgeRepositoryException {
        return null;
    }
}
