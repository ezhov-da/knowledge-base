package ru.ezhov.knowledge.service.view;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeTreeContextNode {
    private boolean expanded = true;
    private String text;
    private String name;
    private List<KnowledgeTreeContextNode> children = new ArrayList<>();
    private boolean leaf;

    KnowledgeTreeContextNode(String text) {
        this.text = text;
        this.name = text;
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

    public List<KnowledgeTreeContextNode> getChildren() {
        return children;
    }

    public void addChildren(KnowledgeTreeContextNode children) {
        long count = this.children.stream().filter(c -> c.getText().equals(children.getText())).count();
        if (count == 0) {
            this.children.add(children);
        }
        leaf = this.children.isEmpty();
    }

    public String getText() {
        int childrenCount = getChildrenCounter();
        return childrenCount == 0 ? text : text + " (" + childrenCount + ")";
    }

    private int getChildrenCounter() {
        int counterChildren = children.size();
        for (KnowledgeTreeContextNode node : children) {
            counterChildren += node.getChildren().size();
        }
        return counterChildren;
    }

    public String getName() {
        return name;
    }
}
