package ru.ezhov.knowledge.core.repository;

import java.util.List;

public interface KnowledgeRepository {
    List<KnowledgeEntity> all() throws KnowledgeRepositoryException;

    KnowledgeEntity getById(String guid) throws KnowledgeRepositoryException;
}
