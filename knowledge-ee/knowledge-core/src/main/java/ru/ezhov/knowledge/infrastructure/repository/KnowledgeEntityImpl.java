package ru.ezhov.knowledge.infrastructure.repository;

import ru.ezhov.knowledge.core.repository.KnowledgeEntity;
import ru.ezhov.knowledge.core.repository.KnowledgeRepository;

class KnowledgeEntityImpl implements KnowledgeEntity {
    private KnowledgeRepository knowledgeRepository;

    private String guid;
    private String name;

    @Override
    public String getGuid() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }
}
