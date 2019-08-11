package ru.ezhov.knowledge.service.view;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KnowledgeTableContext {
    private List<KnowledgeTableContextData> context = new ArrayList<>();

    public KnowledgeTableContext(List<KnowledgeTableContextData> context) {
        this.context = context;
    }

    public static KnowledgeTableContext from(List<KnowledgeClient> knowledgeClients) {
        List<KnowledgeTableContextData> localContext = new ArrayList<>();
        knowledgeClients
                .stream()
                .collect(
                        Collectors.groupingBy(
                                KnowledgeClient::getType,
                                Collectors.summingInt(kc -> 1)
                        )
                )
                .forEach((String k, Integer v) -> localContext.add(new KnowledgeTableContextData(k, v)));
        return new KnowledgeTableContext(localContext);
    }

    public List<KnowledgeTableContextData> getContext() {
        return context;
    }
}
