package ru.ezhov.knowledge.service.view;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class KnowledgeTreeContext {

    static KnowledgeTreeContextNode from(List<KnowledgeClient> knowledgeClients) {
        knowledgeClients.sort(Comparator.comparing(KnowledgeClient::getName));
        Map<String, KnowledgeTreeContextNode> map = new HashMap<>();
        KnowledgeTreeContextNode localRoot = new KnowledgeTreeContextNode("root");
        map.put(null, localRoot);
        for (KnowledgeClient client : knowledgeClients) {
            String knowledgeName = client.getName();
            KnowledgeTreeNodeSupport knowledgeTreeNodeSupport = KnowledgeTreeNodeSupport.from(knowledgeName);
            KnowledgeTreeNodeSupport parent = knowledgeTreeNodeSupport.getParent();
            if (parent == null) {
                KnowledgeTreeContextNode node = map.get(knowledgeTreeNodeSupport.getKey());
                if (node == null) {
                    KnowledgeTreeContextNode withEmptyParentNode = new KnowledgeTreeContextNode(knowledgeTreeNodeSupport.getName());
                    localRoot.addChildren(withEmptyParentNode);
                    map.put(knowledgeTreeNodeSupport.getKey(), withEmptyParentNode);
                }
            } else {
                //Может произойти так, что в карте пока родителей нет, по-этому нам нужно их сгенерировать
                KnowledgeTreeContextNode parentNode = map.get(knowledgeTreeNodeSupport.getParent().getKey());
                if (parentNode == null) {
                    Stack<KnowledgeTreeNodeSupport> nodeStack = new Stack<>();
                    KnowledgeTreeNodeSupport current;
                    current = knowledgeTreeNodeSupport.getParent();
                    while (true) {
                        if (current != null) {
                            nodeStack.push(current);
                            current = current.getParent();
                        } else {
                            break;
                        }
                    }
                    while (!nodeStack.empty()) {
                        KnowledgeTreeNodeSupport treeNode = nodeStack.pop();
                        KnowledgeTreeContextNode children = new KnowledgeTreeContextNode(treeNode.getName());
                        KnowledgeTreeNodeSupport parentWithNull = treeNode.getParent();
                        if (parentWithNull == null) {
                            localRoot.addChildren(children);
                        } else {
                            KnowledgeTreeContextNode parentKey = map.get(parentWithNull.getKey());
                            parentKey.addChildren(children);
                        }
                        map.put(treeNode.getKey(), children);
                    }
                }
                //Получим еще раз после возможного создания
                parentNode = map.get(knowledgeTreeNodeSupport.getParent().getKey());
                KnowledgeTreeContextNode children = new KnowledgeTreeContextNode(knowledgeTreeNodeSupport.getName());
                parentNode.addChildren(children);
                map.put(knowledgeTreeNodeSupport.getKey(), children);
            }
        }
        return localRoot;
    }

    private static class KnowledgeTreeNodeSupport {
        private KnowledgeTreeNodeSupport parent;
        private String key;
        private String name;

        static KnowledgeTreeNodeSupport from(String knowledgeClientName) {
            String[] array = knowledgeClientName.split("-");
            List<String> notEmptyValues = Stream.of(array).filter(a -> !"".equals(a)).collect(Collectors.toList());
            List<String> onlyHierarchy = notEmptyValues.subList(0, notEmptyValues.size() - 1);
            return KnowledgeTreeNodeSupport.from(onlyHierarchy);
        }

        static KnowledgeTreeNodeSupport from(List<String> hierarchy) {
            KnowledgeTreeNodeSupport knowledgeTreeNodeSupport = new KnowledgeTreeNodeSupport();
            if (hierarchy.isEmpty() || hierarchy.size() == 1) {
                knowledgeTreeNodeSupport.parent = null;
                knowledgeTreeNodeSupport.key = String.join("", hierarchy).toLowerCase();
                knowledgeTreeNodeSupport.name = knowledgeTreeNodeSupport.key;
            } else {
                List<String> parents = hierarchy.subList(0, hierarchy.size() - 1);
                knowledgeTreeNodeSupport.key = String.join("", hierarchy).toLowerCase();
                knowledgeTreeNodeSupport.name = hierarchy.get(hierarchy.size() - 1);
                knowledgeTreeNodeSupport.parent = KnowledgeTreeNodeSupport.from(parents);
            }
            return knowledgeTreeNodeSupport;
        }

        KnowledgeTreeNodeSupport getParent() {
            return parent;
        }

        String getKey() {
            return key;
        }

        String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "KnowledgeTreeNodeSupport{" +
                    "parentKey='" + parent + '\'' +
                    ", key='" + key + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
