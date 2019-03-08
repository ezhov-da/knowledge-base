package ru.ezhov.knowledge.service.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnowledgeTreeContext {

    private KnowledgeTreeContextNode root;

    public static KnowledgeTreeContextNode from(List<KnowledgeClient> knowledgeClients) {
        Map<String, KnowledgeTreeContextNode> map = new HashMap<>();
        KnowledgeTreeContextNode localRoot = new KnowledgeTreeContextNode("root");
        for (KnowledgeClient client : knowledgeClients) {
            String knowledgeName = client.getName();
            List<String> parts = parts(knowledgeName);
            int i = 10;
        }

        return localRoot;
    }

    private static List<String> parts(String knowledgeName) {
        String[] array = knowledgeName.split("-");
        return Arrays.asList(array);
    }
}
