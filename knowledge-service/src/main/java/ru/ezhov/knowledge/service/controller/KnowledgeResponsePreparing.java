package ru.ezhov.knowledge.service.controller;

import ru.ezhov.knowledge.common.Knowledge;

interface KnowledgeResponsePreparing {
    String prepare(Knowledge knowledge) throws Exception;
}
