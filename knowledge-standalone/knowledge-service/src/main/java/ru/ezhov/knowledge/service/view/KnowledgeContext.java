package ru.ezhov.knowledge.service.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KnowledgeContext {
    private List<KnowledgeContextData> context = new ArrayList<>();

    public KnowledgeContext(List<KnowledgeContextData> context) {
        this.context = context;
    }

    public static KnowledgeContext from(List<KnowledgeClient> knowledgeClients) {
        List<KnowledgeContextData> localContext = new ArrayList<>();
        knowledgeClients
                .stream()
                .collect(
                        Collectors.groupingBy(
                                KnowledgeClient::getType,
                                Collectors.summingInt(kc -> 1)
                        )
                )
                .forEach((String k, Integer v) -> localContext.add(new KnowledgeContextData(k, v)));
        return new KnowledgeContext(localContext);
    }

    public List<KnowledgeContextData> getContext() {
        return context;
    }
}
