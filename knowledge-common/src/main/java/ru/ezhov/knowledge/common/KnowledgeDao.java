package ru.ezhov.knowledge.common;

import java.util.List;

public interface KnowledgeDao {
    List<Knowledge> getKnowledges(PropertiesHolder propertiesHolder) throws Exception;
}
