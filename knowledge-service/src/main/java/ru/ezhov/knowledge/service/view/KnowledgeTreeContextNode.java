package ru.ezhov.knowledge.service.view;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeTreeContextNode {
    private boolean expanded = true;
    private String text;
    List<KnowledgeTreeContext> children = new ArrayList<>();
    private boolean leaf = children.isEmpty();

    public KnowledgeTreeContextNode(String text) {
        this.text = text;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public List<KnowledgeTreeContext> getChildren() {
        return children;
    }

    public void addChildren(KnowledgeTreeContext children) {
        this.children.add(children);
    }
}
