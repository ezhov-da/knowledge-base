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
            KnowledgeTreeNode knowledgeTreeNode = KnowledgeTreeNode.from(knowledgeName);
            KnowledgeTreeNode parent = knowledgeTreeNode.getParent();
            if (parent == null) {
                KnowledgeTreeContextNode node = map.get(knowledgeTreeNode.getKey());
                if (node == null) {
                    KnowledgeTreeContextNode withEmptyParentNode = new KnowledgeTreeContextNode(knowledgeTreeNode.getName());
                    localRoot.addChildren(withEmptyParentNode);
                    map.put(knowledgeTreeNode.getKey(), withEmptyParentNode);
                }
            } else {
                //Может произойти так, что в карте пока родителей нет, по-этому нам нужно их сгенерировать
                KnowledgeTreeContextNode parentNode = map.get(knowledgeTreeNode.getParent().getKey());
                if (parentNode == null) {
                    Stack<KnowledgeTreeNode> nodeStack = new Stack<>();
                    KnowledgeTreeNode current;
                    current = knowledgeTreeNode.getParent();
                    while (true) {
                        if (current != null) {
                            nodeStack.push(current);
                            current = current.getParent();
                        } else {
                            break;
                        }
                    }
                    while (!nodeStack.empty()) {
                        KnowledgeTreeNode treeNode = nodeStack.pop();
                        KnowledgeTreeContextNode children = new KnowledgeTreeContextNode(treeNode.getName());
                        KnowledgeTreeNode parentWithNull = treeNode.getParent();
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
                parentNode = map.get(knowledgeTreeNode.getParent().getKey());
                KnowledgeTreeContextNode children = new KnowledgeTreeContextNode(knowledgeTreeNode.getName());
                parentNode.addChildren(children);
                map.put(knowledgeTreeNode.getKey(), children);
            }
        }
        return localRoot;
    }

    private static class KnowledgeTreeNode {
        private KnowledgeTreeNode parent;
        private String key;
        private String name;

        static KnowledgeTreeNode from(String knowledgeClientName) {
            String[] array = knowledgeClientName.split("-");
            List<String> notEmptyValues = Stream.of(array).filter(a -> !"".equals(a)).collect(Collectors.toList());
            List<String> onlyHierarchy = notEmptyValues.subList(0, notEmptyValues.size() - 1);
            return KnowledgeTreeNode.from(onlyHierarchy);
        }

        static KnowledgeTreeNode from(List<String> hierarchy) {
            KnowledgeTreeNode knowledgeTreeNode = new KnowledgeTreeNode();
            if (hierarchy.size() == 1) {
                knowledgeTreeNode.parent = null;
                knowledgeTreeNode.key = String.join("", hierarchy).toLowerCase();
                knowledgeTreeNode.name = knowledgeTreeNode.key;
            } else {
                List<String> parents = hierarchy.subList(0, hierarchy.size() - 1);
                knowledgeTreeNode.key = String.join("", hierarchy).toLowerCase();
                knowledgeTreeNode.name = hierarchy.get(hierarchy.size() - 1);
                knowledgeTreeNode.parent = KnowledgeTreeNode.from(parents);
            }
            return knowledgeTreeNode;
        }

        KnowledgeTreeNode getParent() {
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
            return "KnowledgeTreeNode{" +
                    "parentKey='" + parent + '\'' +
                    ", key='" + key + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
