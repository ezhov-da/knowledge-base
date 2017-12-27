package ru.ezhov.knowledge.service.controller;

import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.service.utils.RawTextReader;

class KnowledgeResponsePreparingRaw implements KnowledgeResponsePreparing {
    public String prepare(Knowledge knowledge) throws Exception {
        return new RawTextReader(knowledge.getRawUrl()).read();
    }
}