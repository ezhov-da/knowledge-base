package ru.ezhov.knowledge.service.controller;

import ru.ezhov.knowledge.common.Knowledge;

class KnowledgeResponsePreparingUrl implements KnowledgeResponsePreparing {
    public String prepare(Knowledge knowledge) throws Exception {
        return knowledge.getUrl();
    }
}
